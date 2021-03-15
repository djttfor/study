<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
<a href="${pageContext.request.contextPath}/user/home?pass=456&name=123&pass=789">goHome</a><br>
<a href="${pageContext.request.contextPath}/user/home2?pass=456&pass=789">goHome2</a><br>
<form action="${pageContext.request.contextPath}/user/home2" method="post">
    <input type="checkbox" name="name" value="jimmy">学习
    <input type="checkbox" name="age" value="21">学大习
    <input type="date" name="birthday"><br>
    <input type="checkbox" name="demo.name" value="asss">Demo.name
    <input type="checkbox" name="demo.pass" value="123">Demo.pass
    <input type="checkbox" name="userList[0].id" value="11">list.user.1
    <input type="checkbox" name="userList[0].username" value="123">list.user.1
    <input type="checkbox" name="userList[1].id" value="11">list.user.2
    <input type="checkbox" name="userList[1].username" value="123">list.user.2
    <input type="checkbox" name="map['aaa']" value="bbb">map1
    <input type="checkbox" name="map['ccc']" value="ddd">map2
    <input type="checkbox" name="userMap['aaa'].id" value="123">map3
    <input type="checkbox" name="userMap['aaa'].username" value="ddd">map4
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
