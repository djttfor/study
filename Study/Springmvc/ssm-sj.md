# SSM

## 1.页面跳转

```java
@GetMapping("/home")//只能转发到controller上
public String goHome(){
    return "home";
}
@PostMapping("/home1")
    public String goHome1(){
        return "home";
    }
@GetMapping("/home2")
    public String goHome2(){//可以转发到视图解析器以外的路径
        return "forward:/user/home";//例如：return "forward:/page/cctest.html";
    }//redirect与这个一样

@GetMapping("/home3")
    public ModelAndView goHome3(){
        ModelAndView mv = new ModelAndView();
        //mv.addObject("message","爷回来了");
        mv.setViewName("/home");
        return mv;
    }
@GetMapping("/home5")
    public void goHome5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message","爷back了");
        request.getRequestDispatcher("/user/home").forward(request,response);
    }
 @GetMapping("/home5")
    public void goHome5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+"/user/home");
    }
```

## 2. 数据处理

### 1.对象接受

springmvc 是可以通过参数名称匹配前台传递过来的参数

前台请求如下

```jsp
 <a href="${pageContext.request.contextPath}/user/home?name=123&pass=456">goHome</a><br>
```

```java

@GetMapping("/home")
    public String goHome(String name, @RequestParam("pass")String b, HttpServletRequest request, HttpServletResponse response){
        System.out.println("传递的参数："+name+","+b);
        request.setAttribute("message","传递的参数："+name+","+b);
//        HttpSession session = request.getSession();
//        session.setAttribute("message","传递的参数："+a+","+b);
        return "home";
    }
//通过对象收集前台参数，对象的成员名称必须与前台参数名称一致
@GetMapping("/home")
    public String goHome(Demo demo, HttpServletRequest request, HttpServletResponse response){
        System.out.println("传递的参数："+demo.getName()+","+demo.getPass());
       // request.setAttribute("message","传递的参数："+name+","+b);
        HttpSession session = request.getSession();
        session.setAttribute("message","传递的参数："+demo.getName()+","+demo.getPass());
        return "home";
    }
```

### 2. list接收

```jsp
<a href="${pageContext.request.contextPath}/user/home2?pass=456&pass=789">goHome2</a><br>
<%--或者改成下面的样式--%>
<form action="${pageContext.request.contextPath}/user/home2" method="get">
    <input type="checkbox" name="aihao" value="1">学习
    <input type="checkbox" name="aihao" value="2">学大习
    <input type="checkbox" name="aihao" value="3">学小习
    <input type="submit" value="goHome">
</form>
```

```java
@GetMapping("/home2")
public String goHome2(@RequestParam("pass") List<String> list, HttpServletRequest request){
    System.out.println("传递的参数："+list);
    System.out.println(request.getRemoteHost());
    // request.setAttribute("message","传递的参数："+name+","+b);
    HttpSession session = request.getSession();
    session.setAttribute("message","传递的参数："+list);
    return "home";
}
//post请求参数从请求体中拿
@PostMapping("/home2")
    public String goHome2(@RequestBody List<String> list, HttpServletRequest request){
        System.out.println("传递的参数："+list);
        System.out.println(request.getRemoteHost());
        // request.setAttribute("message","传递的参数："+name+","+b);
        HttpSession session = request.getSession();
        session.setAttribute("message","传递的参数："+list);
        return "home";
    }
```

### 3.获取表单数据发送ajax

前台

```html
<form id="login">
    user:<input type="text" name="name" >
    pass:<input type="text" name="pass" >
    <input type="button" value="goHome">
</form>
```

```js
<script>
    function parseFormDataToObject(str) {//user=11&pass=33&a=5
        str = str.replace(/&/g,"','");
        str = "{'"+str.replace(/=/g,"':'")+"'}";
        return str;
    }
    $(function () {
        $('#login>input[type=button]').click(function () {
            var s = $('#login').serialize();//这个方法适用于拼接url后面的参数，得到的数据：user=11&pass=33
            alert(parseFormDataToObject(s))//要经过进一步的处理拼接成json字符串才能发送
            //"{'user':'aaa','pass':'bbb'}"
            $.ajax({
                    type:"post",
                    url:"/ssx/user/home3",
                    contentType:"application/json;charset=utf-8",
                    dataType:"json",
                    data:parseFormDataToObject(s),
                    success: function (result) {
                        alert(Object.values(result));
                    },
                    error:function (error) {
                        alert("出错了");
                    }
                }
            )
        });
    })
</script>
```

后台

```java
@PostMapping("/home3")
@ResponseBody
public Message goHome3(@RequestBody Demo demo){
    log.info("收到的参数：{}",demo);
    // request.setAttribute("message","传递的参数："+name+","+b);
    return new Message();
}
```