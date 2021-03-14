<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-08-09
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form id="login_form">
    username:<label>
    <input type="text" name="username">
</label><br>
    password:<label>
    <input type="text" name="password">
</label><br>
    生日:<label>
    <input type="date" name="birthday">
</label><br>
    <button type="button" onclick="login()"  style="width: 50px;height: 50px">提交</button>
</form>
<hr/>
<form id="login_form2" action="${pageContext.request.contextPath}/haha/toHello5" method="post">
    username:<label>
    <input type="text" name="username">
</label><br>
    password:<label>
    <input type="text" name="password">
</label><br>
    生日:<label>
    <input type="text" name="birthday">
</label><br>
    银行账户:<label>
    <input type="text" name="account.accountName">
</label><br>
    银行密码:<label>
    <input type="text" name="account.password">
</label><br>
    <button type="submit"  style="width: 50px;height: 50px">提交</button>
</form>
<hr/>
<form id="login_form3" >
    username:<label>
    <input type="text" name="username">
</label><br>
    password:<label>
    <input type="text" name="password">
</label><br>
    生日:<label>
    <input type="text" name="birthday">
</label><br>
    银行账户:<label>
    <input type="text" name="account.accountName">
</label><br>
    银行密码:<label>
    <input type="text" name="account.password">
</label><br>
    <button type="button" onclick="login2()" style="width: 50px;height: 50px">提交</button>
</form>
<script src="js/jquery-3.3.1.js"></script>
<script>
    function formDataToJSON(obj) {
        let params = obj.serializeArray();//需要一个JQuery对象
        let values = {};
        for (let item in params) {
            values[params[item].name] = params[item].value;
        }
        return JSON.stringify(values);
    }
    function login(){
        let data = formDataToJSON($("#login_form"))
        alert(data)
       $.ajax({
           type:"post",
           url:"haha/toHello4",
           contentType:"application/json;charset=utf-8",
           dataType:"json",
           data:data,
           success: function (result) {
               alert(result.username+","+result.password+","+result.birthday+","+result.temp);
           },
           error:function (error) {
               alert("出错了")
           }
           }
       )

        // $.post(
        //     "haha/toHello6",
        //     data,
        //     function (result) {
        //         alert(result.username+","+result.password+","+result.birthday);
        //     },
        //     "json"
        // )
    }
    function login2() {
        let data = formDataToJSON($('#login_form3'));
        alert(data)
    }

</script>
</body>
</html>
