package com.ex.lucene;

import java.io.*;

public class FileUtils {
    public static String readFileContentToString(File path) throws IOException {
        Reader reader = null;
        BufferedReader br = null;
        //StringBuilder sb = null;
        try {
            reader = new FileReader(path);
            br = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String temp = null;
            while((temp = br.readLine())!=null){
                sb.append(temp);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("finally块执行了");
            assert br != null;
            br.close();
        }
        return null;
    }
}
