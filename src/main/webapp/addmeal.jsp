<%--
  Created by IntelliJ IDEA.
  User: arthur
  Date: 3/3/18
  Time: 10:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
    <h2>Add meal:</h2>

    <form action="${pageContext.servletContext.contextPath}/meals?action=addmeal" method="POST">
        description: <input type="text" name="description"/><br/>
        calories: <input type="text" name="calories"/><br/>
        dateTime: <input type="text" name="dateTime"/><br/>
        <input type="submit" value="Add"/>
    </form>

</body>
</html>
