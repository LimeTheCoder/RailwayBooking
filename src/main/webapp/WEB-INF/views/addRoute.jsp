<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <head>
        <jsp:include page="/WEB-INF/views/snippets/header.jsp" />
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/resources/css/auth.css">
    </head>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp" />

<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error" >
            <strong><fmt:message key="error" /></strong> <fmt:message key="${error}" /><br>
        </c:forEach>
    </div>
</c:if>

<div class="container">
    <div class="row main">
        <div class="panel-heading">
            <div class="panel-title text-center">
                <h2 class="title"><fmt:message key="create.new.route" /></h2>
                <hr />
            </div>
        </div>
        <div class="main-login main-center">
            <form class="form-horizontal" method="post">

                <div class="form-group">
                    <label for="from" class="cols-sm-2 control-label"><fmt:message key="request.from" /></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-building-o fa" aria-hidden="true"></i></span>
                            <select name='from' id="from" class="form-control">
                                <c:forEach items="${requestScope.stations}" var="station">
                                    <c:if test="${station.getId() != requestScope.route.getDeparture().getId()}">
                                        <option value="${station.getId()}">${station}</option>
                                    </c:if>
                                    <c:if test="${station.getId() == requestScope.route.getDeparture().getId()}">
                                        <option value="${station.getId()}" selected>${station}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="to" class="cols-sm-2 control-label"><fmt:message key="request.to" /></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-building fa" aria-hidden="true"></i></span>
                            <select name='to' id="to" class="form-control">
                                <c:forEach items="${requestScope.stations}" var="station">
                                    <c:if test="${station.getId() != requestScope.route.getDestination().getId()}">
                                        <option value="${station.getId()}">${station}</option>
                                    </c:if>
                                    <c:if test="${station.getId() == requestScope.route.getDestination().getId()}">
                                        <option value="${station.getId()}" selected>${station}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="train" class="cols-sm-2 control-label"><fmt:message key="route.train" /></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-bus fa" aria-hidden="true"></i></span>
                            <select name='train' id="train" class="form-control">
                                <c:forEach items="${requestScope.trains}" var="train">
                                    <c:if test="${train.getSerialNumber() != requestScope.route.getTrain().getSerialNumber()}">
                                        <option value="${train.getSerialNumber()}">${train}</option>
                                    </c:if>
                                    <c:if test="${train.getSerialNumber() == requestScope.route.getTrain().getSerialNumber()}">
                                        <option value="${train.getSerialNumber()}" selected>${train}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="dep_date" class="cols-sm-2 control-label">
                        <fmt:message key="request.departure.date" />
                    </label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar fa" aria-hidden="true"></i></span>
                            <input type="datetime-local" class="form-control" name="dep_date" id="dep_date"
                                   placeholder="<fmt:message key="request.enter.dep.date" />"
                                   value="<c:out value="${requestScope.route.getDepartureTime()}" />" />
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="dest_date" class="cols-sm-2 control-label">
                        <fmt:message key="route.destination.date" />
                    </label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar fa" aria-hidden="true"></i></span>
                            <input type="datetime-local" class="form-control" name="dest_date" id="dest_date"
                                   placeholder="<fmt:message key="route.enter.dest.date" />"
                                   value="<c:out value="${requestScope.route.getDestinationTime()}" />" />
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="price" class="cols-sm-2 control-label">
                        <fmt:message key="route.price" />
                    </label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-money fa" aria-hidden="true"></i></span>
                            <input type="number" min="0" class="form-control" name="price" id="price"
                                   placeholder="<fmt:message key="route.enter.price" />"
                                   value="<c:out value="${requestScope.route.getPrice()}" />" />
                        </div>
                    </div>
                </div>

                <input class="btn btn-primary btn-lg btn-block login-button" type="submit"
                       value="<fmt:message key="submit" />">
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>
