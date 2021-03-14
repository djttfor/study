<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
<a href="${pageContext.request.contextPath}/user/home?pass=456&name=123&pass=789">goHome</a><br>
<a href="${pageContext.request.contextPath}/user/home2?pass=456&pass=789">goHome2</a><br>
<form action="${pageContext.request.contextPath}/user/home2" method="post">
    <input type="checkbox" name="aihao" value="1">学习
    <input type="checkbox" name="aihao" value="2">学大习
    <input type="checkbox" name="aihao" value="3">学小习
    <input type="submit" value="goHome">
</form>
<form id="login">
    user:<input type="text" name="name" >
    pass:<input type="text" name="pass" >
    <input type="button" value="goHome">
</form>
去A<a href="a.jsp">A</a><br>
去登录<a href="login.jsp">A</a><br>
Cookie测试<a href="${pageContext.request.contextPath}/haha/hehe1">test</a>

<%--${loginMessage}--%>
</body>
<script src="js/jquery-3.3.1.js"></script>
<script>
    function parseFormDataToObject(str) {//user=11&pass=33&a=5
        str = str.replace(/&/g,"','");
        str = "{'"+str.replace(/=/g,"':'")+"'}";
        return str;
    }
    $(function () {
        $('#login>input[type=button]').click(function () {
            var s = $('#login').serialize();
            alert(parseFormDataToObject(s))
            //let d = "{'user':'aaa','pass':'bbb'}"
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
</html>
