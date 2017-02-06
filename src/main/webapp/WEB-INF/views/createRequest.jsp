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
                <h2 class="title"><fmt:message key="create.new.request" /></h2>
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
                                    <c:if test="${station.getId() != requestScope.request.getDeparture().getId()}">
                                        <option value="${station.getId()}">${station}</option>
                                    </c:if>
                                    <c:if test="${station.getId() == requestScope.request.getDeparture().getId()}">
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
                                    <c:if test="${station.getId() != requestScope.request.getDestination().getId()}">
                                        <option value="${station.getId()}">${station}</option>
                                    </c:if>
                                    <c:if test="${station.getId() == requestScope.request.getDestination().getId()}">
                                        <option value="${station.getId()}" selected>${station}</option>
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
                            <input type="date" class="form-control" name="dep_date" id="dep_date"
                                   placeholder="<fmt:message key="request.enter.dep.date" />"
                                   value="<c:out value="${requestScope.request.getDepartureTime()}" />" />
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
