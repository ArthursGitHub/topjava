<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
    <head>
        <title>Users</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <h2>User #<c:out value = "${userId}"/> is choosen</h2>
    </body>
</html>