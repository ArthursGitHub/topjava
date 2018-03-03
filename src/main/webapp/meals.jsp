<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <table border="1">
        <thead>
        <tr>
            <th>#</th>
            <th>description</th>
            <th>calories</th>
            <th>dateTime</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${mealList}" var="meal">
            <c:set var="item" value="${item + 1}"/>

            <c:set var="color" value="${meal.exceed ?  'background-color: red;':'background-color: green;'}"/>
            <tr style="${color}">

                <td>${item}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" var="dateTime"/>
                    <c:out value="${dateTime}"/>
                </td>
                <td><a href="addmeal?action=editmeal&mealId=<c:out value="${meal.id}"/>">Edit</a></td>
                <td><a href="addmeal?action=deletemeal&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>

        </c:forEach>
        </tbody>

    </table>

    <a href="addmeal?action=addmeal">Add meal</a>
</body>
</html>
