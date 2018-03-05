<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Edit meal</title>
</head>
<body>
    <h2>Edit meal:</h2>
    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" var="dateTime"/>

    <form action="${pageContext.servletContext.contextPath}/meals?action=updatemeal" method="POST">


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
                <td><input type="text" name="description" value="${meal.description}"/></td>
            </tr>
            <tr>
                <td>calories:</td>
                <td><input type="text" name="calories" value="${meal.calories}"/></td>
            </tr>
            <tr>
                <td>dateTime:</td>
                <td><input type="text" name="dateTime" value="${dateTime}"/></td>
            </tr>
            </tbody>
        </table>


        <%--description: <input type="text" name="description" value="${meal.description}"/><br/>--%>
        <%--calories: <input type="text" name="calories" value="${meal.calories}"/><br/>--%>
        <%--dateTime: <input type="text" name="dateTime" value="${dateTime}"/><br/>--%>
        <input type="hidden" name="mealId" value="${meal.id}">

        <input type="submit" value="Save"/>

    </form>

</body>
</html>
