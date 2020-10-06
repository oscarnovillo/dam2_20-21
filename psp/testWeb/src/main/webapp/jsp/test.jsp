<%--
  Created by IntelliJ IDEA.
  User: oscar
  Date: 9/28/2020
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%= request.getAttribute("m")%>
<br>
${m}
<br>
<%= request.getRequestURL().toString()%>
</body>
</html>
