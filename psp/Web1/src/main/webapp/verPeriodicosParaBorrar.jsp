<%--
  Created by IntelliJ IDEA.
  User: oscar
  Date: 25/03/2021
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="POST" action="delperiodicos">
<select name="nombre">

    <c:forEach var="item" items="${periodicos}">
        <option value="<c:out  value="${item.nombre}" />">
            <c:out value="${item.nombre}"/>
        </option>

    </c:forEach>
</select>
    <input type="submit" value="borrar" />
</form>
</body>
</html>
