package com.ex.test;


import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

public class LuceneTest {

    /*
    第一步：创建一个java工程，并导入jar包。
    第二步：创建一个indexwriter对象。
            1）指定索引库的存放位置Directory对象
            2）指定一个IndexWriterConfig对象。
    第二步：创建document对象。
    第三步：创建field对象，将field添加到document对象中。
    第四步：使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
    第五步：关闭IndexWriter对象。`
    */
    public void createIndex() throws Exception {
        Directory directory = FSDirectory.open(new File("F:\\LuceneIndex\\index").toPath());
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        File dir = new File("F:\\工作");
        for (File file : dir.listFiles()) {
            String fileName = file.getName();
            String fileContent = FileUtils.readFileToString(file);
            String filePath = file.getPath();
            long fileSize = FileUtils.sizeOf(file);

            Field fileNameF = new TextField("fileName",fileName,Field.Store.YES);
            Field fileContentF = new TextField("fileContent",fileContent,Field.Store.YES);
            Field filePathF = new TextField("filePath",filePath,Field.Store.YES);
            Field fileSizeF = new TextField("fileSize",fileSize+"",Field.Store.YES);

            Document document = new Document();
            document.add(fileNameF);
            document.add(fileContentF);
            document.add(filePathF);
            document.add(fileSizeF);

            indexWriter.addDocument(document);
        }
        indexWriter.close();

    }

    /**
     * lucene8.0创建索引的方式
     */
    public void createIndex8p0(){

    }

    public IndexSearcher preparedQuery() throws IOException {
        Directory directory = FSDirectory.open(new File("F:\\LuceneIndex\\index").toPath());
        IndexReader indexReader = DirectoryReader.open(directory);
        return new IndexSearcher(indexReader);
    }
    public void close(IndexSearcher indexSearcher){
        IndexReader indexReader = indexSearcher.getIndexReader();
        try {
            if (indexReader != null) {
               indexReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 精准查找
     * @throws IOException
     */
    public void termQuery(String fieldName,String content) throws IOException {
        IndexSearcher indexSearcher = preparedQuery();
        Query query = new TermQuery(new Term(fieldName,content));
        bl(query,indexSearcher);

    }
    /**
     * 范围查找
     */
    public void rangeQuery(long r1,long r2) throws IOException {
        IndexSearcher indexSearcher = preparedQuery();
        Query query = LongPoint.newRangeQuery("fileSize",r1,r2);
        bl(query,indexSearcher);
    }

    /**
     * 遍历document
     */
    public void bl(Query query,IndexSearcher indexSearcher) throws IOException {
        TopDocs topDocs = indexSearcher.search(query,10);
        System.out.println("查询到的总条数为:"+topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            System.out.println("document的id:"+scoreDoc.doc);
            Document document = indexSearcher.doc(scoreDoc.doc);
            String fileName = document.get("fileName");
            System.out.println("文件名:"+fileName);
            String fileContent = document.get("fileContent");
            System.out.println("文件内容:"+fileContent);
            String filePath = document.get("filePath");
            System.out.println("文件路径:"+filePath);
            String fileSize = document.get("fileSize");
            System.out.println("文件大小:"+fileSize);
            System.out.println("------------------------------------------------------");
        }
        close(indexSearcher);
    }
    public void  delete() throws IOException {
        Directory directory = FSDirectory.open(new File("F:\\LuceneIndex\\index").toPath());
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
        Query query = new TermQuery(new Term("fileContent","spring"));
        //indexWriter.deleteDocuments(new TermQuery(new Term("fileContent","spring")));
        indexWriter.close();
    }

    public void showStandardAnalyzer() throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("hehe","my,my,my name is van ,the deep dark fantasy");
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
       // OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            //System.out.println("开始:"+offsetAttribute.startOffset());
            System.out.println(charTermAttribute);
           // System.out.println("结束:"+offsetAttribute.endOffset());
        }
    }

    @Test
    public void test1() throws Exception {
        termQuery("fileContent","spring");
        //rangeQuery(1L,1000000L);
        //delete();
    }
    @Test
    public void test2() throws Exception {
      //创建索引
        createIndex();
        //查看分词效果
        //showStandardAnalyzer();
    }
}
