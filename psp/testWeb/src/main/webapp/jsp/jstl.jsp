<%--
  Created by IntelliJ IDEA.
  User: oscar
  Date: 9/28/2020
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<img src="/primera/assert/img/gato.jpg" />
<h1>Hello World!</h1>

<c:out value="${test}"/>
<c:out value="${jjj}"/>
<table border="1">
    <c:forEach var="item" items="${numList}"  >
        <tr><td>${item}</td></tr>
    </c:forEach>
</table>
</body>
</html>

