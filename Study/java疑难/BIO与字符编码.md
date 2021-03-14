### BIO

#### 1.BIO大纲

![image-20210106102538355](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210106102538355.png)

#### 2.使用小结

1）判断操作的数据类型
纯文本数据：读用Reader系，写用Writer系
非纯文本数据：读用InputStream系，写用OutputStream系
如果纯文本数据只是简单的复制，下载，上传，不对数据内容本身做处理，那么使用Stream系
2）判断操作的物理节点
内存：ByteArrayXXX
硬盘：FileXXX
网络：http中的request和response均可获取流对象，tcp中socket对象可获取流对象
键盘（输入设备）：System.in
显示器（输出设备）：System.out
3）搞清读写顺序，一般是先获取输入流，从输入流中读取数据，然后再写到输出流中。
4）是否需增加特殊功能，如需要用缓冲提高读写效率则使用BufferedXXX，如果需要获取文本行号，则使用LineNumberXXX，如果需要转换流则使用InputStreamReader和OutputStreamWriter，如果需要写入和读取对象则使用ObjectOutputStream和ObjectInputStream

#### 3.注意事项

##### 1.换行

Windows下用\r\n, linux下\n,mac下\r

##### 2.几个常见字符集相互编码解码结果对比,看看是否都能完成中文还原

```java
//测试代码如下,比较的编码方式有GBK,UTF-8,ISO8859-1
public static void demo1(String charset1,String charset2,String temp) throws UnsupportedEncodingException {
        byte[] bytes1 = temp.getBytes(charset1);
        String str = new String(bytes1, charset2);
        System.out.println(charset1+":"+Arrays.toString(bytes1)+" 字节数:"+bytes1.length);
        System.out.println(str);
        byte[] bytes = str.getBytes(charset2);
        System.out.println(charset2+":"+Arrays.toString(bytes)+" 字节数:"+bytes.length);
        System.out.println(new String(bytes, charset1));
    }
public static void main(String[] args) throws UnsupportedEncodingException {
        demo1("utf-8","ISO8859-1","今晚打hhh");//倒转的也会比较
    }
```

###### 2.1 UTF-8与ISO8859-1 

UTF编码,ISO8859-1解码,结果如下

![image-20210108112828881](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108112828881.png)

这个组合能够还原中文,首先utf8的中文编码是3个字节,而ISO8859-1字符集没有中文,所以会得到如上图第一列的乱码,这里的乱码就是上图第二列byte对应的ISO88959-1的字符,首先第一个码值-28转换为ISO8859-1的码值是E4,计算方式如下,

![image-20210108104521346](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108104521346.png)

这里是ISO8859-1的源码截图,计算方式是(数值&255)所以-28&255=228,16进制为E4,那么我们去对照ISO8859-1的码表

![image-20210108095050207](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108095050207.png)

![image-20210108095100503](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108095100503.png)

以此类推,-69转化16进制BB,十进制187,-118转化16进制8A,十进制138,分别在码表对应的字符为

![image-20210108095520766](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108095520766.png)

![image-20210108095549245](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108095549245.png)

刚好对应上结果图的第一列的前三个字符,那么这前三个字节16进制为E4 BB 8A,对应UTF-8码表正好是***今***这个字符.

![image-20210108100123703](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108100123703.png)

解释一下上图,第一个是GBK,第二个是UTF-16,第三到五是UTF-8,最后是汉字

那么最后分析吧"今晚打hhh"首先经过UTF-8编码,每个汉字对应三个字节,所以是9个字节,每个字母都是一个字节,共三个字节,而且对应的码值是ASCII码值(几乎的编码方式都支持ASCII码,所以英文几乎不会乱码),那么经过ISO8859-1解码后,所谓的解码就是,通过计算得出的码值到它的码表里面找到它对于的字符,所以才会得出那一串乱码.

如果反过来先用ISO8859-1编码,再用UTF8解码,结果如下

![image-20210108112748448](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108112748448.png)

首先前三个?字符首先是因为ISO8859-1不支持中文,所以在自己的码表上找不到对于的码值,所以使用了?字符的码值代替这(?的ASCII码值为63),UTF-8在解码的时候会将0-127的码值直接转换为ASCII字符,所以就得到了上述结果.

###### 2.2 GBK与UTF-8

```java
demo1("GBK","UTF-8","今晚打hhh");
```

![image-20210108113810196](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108113810196.png)

UTF-8三个字节对应一个中文字符,所以得出了很奇怪的符号,后面转换回来少了一个h是因为,转换回来的数组长度为18,刚好可以两两配对.

```java
demo1("UTF-8","GBK","今晚打hhh");
```

![image-20210108113929116](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210108113929116.png)

GBK两个字节对应一个中文字符,那么-28,-69分别(&255)得出E4BB(转HEX)对应的中文就是浠

总结:UTF-8是三个字节一个中文,GBK是两个字节一个中文,少字节编码,多字节解码时由于在自己的码表里面找不到对于的字符,所以就显示为乱码(一般都为?或者�)或者一些奇怪的符号,再用多字节编码时候就会得到这些奇怪符号的码值,少字节解码时,虽然可以找到对应的值,但是都已经不是原来的值了. 而多字节编码,少字节解码时由于都可以找到对应的字符,不会显示为乱码,故在逆推之后,还是原来那些码值,所以能够还原出来.

#### 4.利用了ISO8859-1的中文加密

```java
private static final String BASIC_DIRECTORY = "C:\\Users\\DajieWei\\Desktop\\";
public static void byteStreamW(){
    InputStream in = null;
    OutputStream out = null;
    try{
        byte[] bytes = new String(("今晚打老虎,The deep dark fantasy").getBytes(), StandardCharsets.ISO_8859_1).getBytes();
        in = new ByteArrayInputStream(bytes);
        out = new FileOutputStream(BASIC_DIRECTORY+"xxx.txt");
        int len = 0;
        byte[] buff = new byte[1024];
        while((len = in.read(buff))!=-1){
            out.write(buff,0,len);
        }
    }catch (IOException e){
        e.printStackTrace();
    }finally {
        closeAll(out,in);
    }
}
public static void byteStreamR(){
        FileInputStream fi = null;
        ByteArrayOutputStream out =null;
        try{
            fi = new FileInputStream(BASIC_DIRECTORY+"xxx.txt");
            System.out.println("字节数:"+fi.available());
            out = new ByteArrayOutputStream();
            int len =0;
            byte[] buf = new byte[1024];
            while((len=fi.read(buf))!=-1){
                out.write(buf,0,len);
            }
            ByteBuffer encode = StandardCharsets.ISO_8859_1.encode(out.toString());
            System.out.println(new String(encode.array()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
```

#### 5.优雅的关闭所有的流

```java
public static void closeAll(Closeable...closeables){
    for (Closeable closeable : closeables) {
        try {
            if (closeable !=null){
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### 6.获取键盘输入的字符的两种方式

```java
BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
Scanner scanner = new Scanner(System.in);//与前一种相比,该方式接收到非空格,tab,换行以外任一字符后再按换行才能接收,前一种方法按了换行结束直接接收
```

#### 7.打印流PrintStream 与 PrintWriter

#####  7.1 打印异常到文件

```java
public static void printExceptionToFile1(){//字符流
        PrintWriter printStream = null;
        FileInputStream fileInputStream = null;
        try{
            printStream = new PrintWriter(new FileWriter(BASIC_DIRECTORY+"xx.txt",true),true);
            fileInputStream = new FileInputStream(BASIC_DIRECTORY);

        }catch (IOException e){
            e.printStackTrace();
            e.printStackTrace(printStream);
        }finally {
            closeAll(fileInputStream,printStream);
        }
    }
    public static void printExceptionToFile2(){//字节流
        PrintStream printStream = null;
        FileInputStream fileInputStream = null;
        try{
            printStream = new PrintStream(new FileOutputStream(BASIC_DIRECTORY+"xx.txt",true));
            fileInputStream = new FileInputStream(BASIC_DIRECTORY);
        }catch (IOException e){
            e.printStackTrace();
            e.printStackTrace(printStream);
        }finally {
            closeAll(fileInputStream,printStream);
        }
    }
```

##### 7.2 获取详细的异常信息

```java
public static String getExceptionMessage(Throwable throwable){//字符流
    PrintWriter printWriter = null;
    StringWriter stringWriter = null;
    try {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        if (throwable != null) {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        }else{
            return null;
        }
    } finally {
        closeAll(printWriter);
    }
}
public static String getExceptionMessage2(Throwable throwable){//字节流
        PrintStream  printStream = null;
        ByteArrayOutputStream outputStream = null;
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        if(throwable!=null){
            throwable.printStackTrace(printStream);
            return outputStream.toString();
        }else{
            return null;
        }
    }
```