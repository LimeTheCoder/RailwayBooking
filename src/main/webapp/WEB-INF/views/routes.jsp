<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp" />
    <style>
        .custab{
            border: 1px solid #ccc;
            padding: 5px;
            margin: 5% 0;
            box-shadow: 3px 3px 2px #ccc;
            transition: 0.5s;
        }
        .custab:hover{
            box-shadow: 3px 3px 0px transparent;
            transition: 0.5s;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/snippets/navbar.jsp" />


    <div class="container">
        <div class="row col-md-10 col-md-offset-1 custyle">

            <c:if test="${not empty sessionScope.user_request}">
                <div class="panel panel-success">
                    <div class="panel-heading"><fmt:message key="your.request" /></div>
                    <div class="panel-body">
                        <div class="table">
                            <table class="table table-stripped table-bordered table-hover">
                                <tbody>
                                <tr>
                                    <td><fmt:message key="request.from" /></td>
                                    <td>${sessionScope.user_request.getDeparture()}</td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="request.to" /></td>
                                    <td>${sessionScope.user_request.getDestination()}</td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="request.departure.date" /></td>
                                    <td><fmt:formatDate type="date" dateStyle="long"
                                                        value="${sessionScope.user_request.getDepartureTime()}"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            <div class="panel-heading">
                <div class="panel-title text-center">
                    <h1 class="title"><fmt:message key="request.results" /></h1>
                    <hr />
                </div>
            </div>
            </c:if>

            <c:if test="${empty sessionScope.user_request}">
                <div class="panel-heading">
                    <div class="panel-title text-center">
                        <h1 class="title"><fmt:message key="routes.schedule" /></h1>
                        <hr />
                    </div>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${not empty requestScope.routes}">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th><fmt:message key="route.train" /></th>
                    <th><fmt:message key="request.from" /></th>
                    <th><fmt:message key="request.to" /></th>
                    <th><fmt:message key="route.date" /></th>
                    <th><fmt:message key="route.time" /></th>
                    <th><fmt:message key="route.price" /></th>
                    <th><fmt:message key="route.free" /></th>
                    <c:if test="${not empty sessionScope.user_request}">
                        <th><fmt:message key="route.action" /></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="route" items="${requestScope.routes}">
                    <tr>
                        <td>${route.getTrain()}</td>
                        <td>${route.getDeparture()}</td>
                        <td>${route.getDestination()}</td>
                        <td><fmt:formatDate type="date" dateStyle="long"
                                            value="${route.getDepartureTime()}"/></td>
                        <td><fmt:formatDate type="time" dateStyle="medium"
                                            value="${route.getDepartureTime()}"/></td>
                        <td>${route.getPrice()}$</td>
                        <td>${route.getFreePlaces()}</td>
                        <c:if test="${not empty sessionScope.user_request}">
                            <td>
                            <form action="${pageContext.request.contextPath}/site/user/invoices/new" method="POST">
                                <input type="hidden" name="route" value="${route.getId()}">
                                <button type="submit" class='btn btn-info btn-xs'>
                                    <span class="glyphicon glyphicon-plane"></span><fmt:message key="route.reserve" />
                                </button>

                            </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    <strong><fmt:message key="info" /></strong> <fmt:message key="routes.no.matches" />
                </div>
            </c:otherwise>
            </c:choose>
        </div>
    </div>


    <jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>