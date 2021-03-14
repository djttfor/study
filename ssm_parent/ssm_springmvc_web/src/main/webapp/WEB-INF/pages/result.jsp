<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-02-23
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>保存结果</title>
</head>
<body>

${requestScope.message}

<c:if test="${requestScope.result!=1}">
    <h3 style="color: red;font-size: 30px">保存失败</h3>
</c:if>
<c:if test="${requestScope.result==1}">
    <h3 style="color: green;font-size: 30px">保存成功</h3>
</c:if>

</body>
</html>
