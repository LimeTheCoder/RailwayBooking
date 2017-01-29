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
                    <div class="panel-heading">Your request</div>
                    <div class="panel-body">
                        <div class="table">
                            <table class="table table-stripped table-bordered table-hover">
                                <tbody>
                                <tr>
                                    <td>From</td>
                                    <td>${sessionScope.user_request.getDeparture()}</td>
                                </tr>
                                <tr>
                                    <td>To</td>
                                    <td>${sessionScope.user_request.getDestination()}</td>
                                </tr>
                                <tr>
                                    <td>Departure date</td>
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
                    <h1 class="title">Results for your request</h1>
                    <hr />
                </div>
            </div>
            </c:if>

            <c:if test="${empty sessionScope.user_request}">
                <div class="panel-heading">
                    <div class="panel-title text-center">
                        <h1 class="title">Routes schedule</h1>
                        <hr />
                    </div>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${not empty requestScope.routes}">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th>Train No</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Date</th>
                    <th>Time, EEST</th>
                    <th>Price</th>
                    <th>Free places</th>
                    <c:if test="${not empty sessionScope.user_request}">
                        <th>Action</th>
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
                            <form action="${pageContext.request.contextPath}/site/invoices/new" method="POST">
                                <input type="hidden" name="route" value="${route.getId()}">
                                <button type="submit" class='btn btn-info btn-xs'>
                                    <span class="glyphicon glyphicon-plane"></span>Reserve
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
                No routes that matches your request.
            </c:otherwise>
            </c:choose>
        </div>
    </div>


    <jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>