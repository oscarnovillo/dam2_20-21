<%--
  Created by IntelliJ IDEA.
  User: oscar
  Date: 25/03/2021
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<c:if test="${mostraralert}" >
<script>
    alert("${alert}");
</script>
</c:if>





LISTADO DE PERIODICOS

<img src="img/img.jpg" width="100px" height="100px"/>


<br>

<table border="1" >
<c:forEach var="item" items="${periodicos}">
    <tr>
        <td > <c:out  value="${item.nombre}"/></td>
        <td > <c:out  value="${item.precio}"/></td>
    </tr>
</c:forEach>
</table>

<a href="addPeriodico.html" >add periodico</a>
&nbsp;
<a href="verperiodicosactualizar" >update periodico</a>
&nbsp;
<a href="verperiodicosborrar" >del periodico</a>
</body>
</html>
