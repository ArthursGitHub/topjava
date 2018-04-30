<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealsDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>

    <div class="modal fade" tabindex="-1" id="editRow">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form id="detailsForm">
                        <input type="hidden" id="id" name="id">

                        <div class="form-group">
                            <label for="datetime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                            <input type="datetime-local" class="form-control" id="datetime" name="localDateTime" required >

                        </div>

                        <div class="form-group">
                            <label for="description" class="col-form-label"><spring:message code="meal.description"/></label>
                            <input type="text" class="form-control" id="description" size=40 name="description" required >
                        </div>

                        <div class="form-group">
                            <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                            <input type="number" class="form-control" id="calories" name="calories" required >
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                        <span class="fa fa-close" aria-hidden="true"></span>
                    </button>
                    <button type="button" class="btn btn-primary" onclick="save()">
                        <span class="fa fa-check" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="jumbotron">
        <div class="container">
            <h3><spring:message code="meal.title"/></h3>
            <br/>
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="meal.add"/>
            </button>
            <table class="table table-striped" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                    <tr id="${meal.id}" data-mealExceed="${meal.exceed}">
                        <td>${fn:formatDateTime(meal.dateTime)}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a><span class="fa fa-pencil"></span></a></td>
                        <td><a class="delete"><span class="fa fa-remove"></span></a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>