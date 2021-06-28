<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/29
  Time: 1:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h3>The deep dark fantasy呵呵呵呵呵</h3>
    <table border="1px red solid" style="border-collapse: collapse">
        <tr>
            <th>id</th>
            <th>账户名</th>
            <th>密码</th>
            <th>余额</th>
        </tr>
    <c:forEach items="${requestScope.allAccount}"  var="account">
        <tr>
            <td>${account.aid}</td>
            <td>${account.acname}</td>
            <td>${account.password}</td>
            <td>${account.money}</td>
        </tr>
    </c:forEach>
    </table>

<% System.out.println("success页面执行了");%>
</body>
</html>
