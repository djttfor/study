<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-02-23
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>保存账户</title>
</head>
<body>
<form action="/account/save" method="post">
    账户名:<input type="text" name="acname"><br>
    密码:<input type="text" name="password"><br>
    uid:<input type="text" name="uid"><br>
    money:<input type="text" name="money"><br>
    <input type="submit" value="保存">
</form>

<form action="/account/transfer" method="post">
    转出:<input type="text" name="out">
    转入:<input type="text" name="in">
    金额:<input type="text" name="money">
    <input type="submit" value="转账">
</form>
</body>
</html>
