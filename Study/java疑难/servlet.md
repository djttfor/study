### Servlet

#### 1.在tomcat安装目录部署项目的三种方式

#### 1.1 在conf/catalina/localhost配置

创建一个xml文件,在里面写war包路径或解压的war包资源如下

<Context docBase = "F://xxx/xxx/ssm.war"/>

虚拟路径为xml文件的名字

#### 1.2 在webapps下放war包即可,tomcat自动解压

#### 1.3在conf/server.xml中配置

找到Host节点在下面创建一个Context节点填写项目路径

<Context path="/xxx" docBase = "F://xxx/xxx/ssm"/>

这种部署方式路径不能写war包路径,要先把war包解压,填文件夹路径

#### 1.4 在webapps下创建一个文件，在里面添加WEB-INF/classes路径，然后把编译后的servlet或者静态资源放入即可

## 2.文件下载

只需注意设置响应头即可,不设置响应头就会直接展示图片

```java
String realPath = req.getServletContext().getRealPath("images\\周数.png");
String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);
resp.setHeader("content-disposition",
        "attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
InputStream in = new FileInputStream(realPath);
ServletOutputStream out = resp.getOutputStream();
int len;
byte[] buff =new byte[1024*2];
while((len=in.read(buff))!=-1){
    out.write(buff,0,len);
}
```

## 3.文件上传

所需jar包

```xml
<!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.4</version>
</dependency>
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```

原生上传代码

```java
//表单提交方法一定要为post, 同时要设置属性enctype=multipart/form-data
private static final long serialVersionUID = 1L;

// 上传文件存储目录
private static final String UPLOAD_DIRECTORY = "upload";

// 上传配置
private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

/**
 * 上传数据及保存文件
 */
protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
    // 检测是否为多媒体上传
    if (!ServletFileUpload.isMultipartContent(request)) {
        // 如果不是则停止
        PrintWriter writer = response.getWriter();
        writer.println("Error: 表单必须包含 enctype=multipart/form-data");
        writer.flush();
        return;
    }

    // 配置上传参数
    DiskFileItemFactory factory = new DiskFileItemFactory();
    // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中C:\Users\DajieWei\AppData\Local\Temp\
    factory.setSizeThreshold(MEMORY_THRESHOLD);
    // 设置临时存储目录
    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

    ServletFileUpload upload = new ServletFileUpload(factory);

    // 设置最大文件上传值
    upload.setFileSizeMax(MAX_FILE_SIZE);

    // 设置最大请求值 (包含文件和表单数据)
    upload.setSizeMax(MAX_REQUEST_SIZE);

    // 中文处理
    upload.setHeaderEncoding("UTF-8");

    // 构造临时路径来存储上传的文件
    // 这个路径相对当前应用的目录
    String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;

    // 如果目录不存在则创建
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
        uploadDir.mkdir();
    }

    try {
        // 解析请求的内容提取文件数据
        @SuppressWarnings("unchecked")
        List<FileItem> formItems = upload.parseRequest(request);

        if (formItems != null && formItems.size() > 0) {
            // 迭代表单数据
            for (FileItem item : formItems) {
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    // 在控制台输出文件的上传路径
                    System.out.println(filePath);
                    // 保存文件到硬盘
                    item.write(storeFile);
                    request.setAttribute("message",
                            "文件上传成功!");
                }
            }
        }
    } catch (Exception ex) {
        request.setAttribute("message",
                "错误信息: " + ex.getMessage());
    }
    // 跳转到 message.jsp
    request.getServletContext().getRequestDispatcher("/").forward(
            request, response);
}
```

#### 4.war包与jar包的局别

一般来说jar包就是一种依赖，对于tomcat来说，如果用jar包来部署项目，需要各种判断是否应该部署

而war包对tomcat来说就是一个应用程序，放到tomcat安装目录下的webapps，tomcat会自动将其解压然后部署

总的来说没有太大的区别，名字不同好区分而已。

#### 5.tomcat的布局 具体可查看conf/server.xml

tomcat架构俯视图

![image-20210226152226840](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210226152226840.png)

- Engine 

  引擎名称默认是Catalina，默认主机是 localhost

- Host

  可以设置域名，不过只有自己方法有效，默认去扫描webapps路径下去部署应用，也可以
  用Context 标签声明一个应用

- Context

  代表一个应用，存储了多个Wrapper

- Wrapper 

  封装了servlet

  Pipeline 管道  每个节点都由管道连接

  valve 阀门 如果想自己定义阀门，只需继承RequestFilterValve即可


## FilterChain

```java
/*
org.apache.catalina.core.ApplicationFilterFactory第84行，从Context中获取FilterMap,
这个FilterMap包含全部的拦截路径
*/

 // Add the relevant path-mapped filters to this filter chain
        for (FilterMap filterMap : filterMaps) {
            if (!matchDispatcher(filterMap, dispatcher)) {
                continue;
            }
            if (!matchFiltersURL(filterMap, requestPath))
                continue;
            ApplicationFilterConfig filterConfig = (ApplicationFilterConfig)
                    context.findFilterConfig(filterMap.getFilterName());
            if (filterConfig == null) {
                // FIXME - log configuration problem
                continue;
            }
            filterChain.addFilter(filterConfig);
        }

//请求路径与拦截路径对比
private static boolean matchFiltersURL(String testPath, String requestPath) {

        if (testPath == null)
            return false;

        // Case 1 - Exact Match
        if (testPath.equals(requestPath))
            return true;

        // Case 2 - Path Match ("/.../*")
        if (testPath.equals("/*"))
            return true;
        if (testPath.endsWith("/*")) {
            if (testPath.regionMatches(0, requestPath, 0,
                                       testPath.length() - 2)) {
                if (requestPath.length() == (testPath.length() - 2)) {
                    return true;
                } else if ('/' == requestPath.charAt(testPath.length() - 2)) {
                    return true;
                }
            }
            return false;
        }

        // Case 3 - Extension Match
        if (testPath.startsWith("*.")) {
            int slash = requestPath.lastIndexOf('/');
            int period = requestPath.lastIndexOf('.');
            if ((slash >= 0) && (period > slash)
                && (period != requestPath.length() - 1)
                && ((requestPath.length() - period)
                    == (testPath.length() - 1))) {
                return testPath.regionMatches(2, requestPath, period + 1,
                                               testPath.length() - 2);
            }
        }

        // Case 4 - "Default" Match
        return false; // NOTE - Not relevant for selecting filters

    }

```

```properties
-Dfile.encoding=UTF-8   //IDEA-> Help -> Edit Custom VM Options添加
java.util.logging.ConsoleHandler.encoding = UTF-8   //tomcat安装目录/conf/logging/修改为UTF-8
```

## 生产SessionID

生产sessionId调用的方法

```
org.apache.catalina.util.StandardSessionIdGenerator#generateSessionId(null)
```

## Session的生成

session是HttpServletRequest的字段

### 1.如果不存在Session

> 那么会在第一次通过request.getSession(boolean create),如果create为true则创建一个会话，并将SessionID存入Key为JSESSIONID的Cookie中



### 2.如果每次请求能从Cookie中获取到SessionID

>那么会在内部的ConcurrentHashMap中获取Session，地点在ManagerBase的742行

### 3.SessionID

> SessionId是在第一次通过**request.getSession(true)**的执行中生成的

## 请求转发与重定向的区别

### 请求转发：

```java
request.getRequestDispatcher("URI地址").forward(request,response);
```

### 重定向：

```java
response.setStatus(302);//设置状态码
response.setHeader("Location","URL地址");
//或者
response.setRedirect("URL地址");
```

### 区别

- 重定向是两次请求，转发是一次请求
- 重定向是浏览器行为，请求转发是服务器行为
- 重定向浏览器的地址会发生改变，转发不会
- 重定向可以重定向到任何地址，转发只能在项目内转发

