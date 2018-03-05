<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add meal</title>
</head>
<body>
    <h2>Add meal:</h2>

    <form action="${pageContext.servletContext.contextPath}/meals?action=addmeal" method="POST">
        <table border="1">
            <thead>
            <tr>
                <th>item</th>
                <th>value</th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td>description:</td>
                <td><input type="text" name="description"/></td>
            </tr>
            <tr>
                <td>calories:</td>
                <td><input type="text" name="calories"/></td>
            </tr>
            <tr>
                <td>dateTime:</td>
                <td><input type="text" name="dateTime"/></td>
            </tr>
            </tbody>
        </table>

        <input type="submit" value="Add"/>
    </form>

</body>
</html>
