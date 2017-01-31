<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp" />

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/card.css">

</head>
<body>
    <jsp:include page="/WEB-INF/views/snippets/navbar.jsp" />

    <div class="container">
        <div class="panel-heading">
            <div class="panel-title text-center">
                <h1 class="title">Invoices</h1>
                <hr />
            </div>
        </div>

        <div class="row">
            <c:forEach var="invoice" items="${requestScope.invoices}">
                <div class="col-md-4">
                    <c:if test="${invoice.getStatus().name().equals('PAID')}">
                        <div class="offer offer-radius offer-primary">
                    </c:if>
                    <c:if test="${invoice.getStatus().name().equals('PENDING')}">
                        <div class="offer offer-radius offer-warning">
                    </c:if>
                    <c:if test="${invoice.getStatus().name().equals('REJECTED')}">
                        <div class="offer offer-radius offer-default">
                    </c:if>
                        <div class="shape">
                            <div class="shape-text">
                                bill
                            </div>
                        </div>
                        <div class="offer-content">
                            <h3 class="lead">
                                Invoice #<c:out value="${invoice.getId()}" />
                            </h3>
                            <div class='col-md-12'>
                                <p>Passenger: <c:out value="${invoice.getRequest().getPassenger()}" /></p>
                                <p>Status: <c:out value="${invoice.getStatus()}" /></p>
                                <p>Destination: <c:out value="${invoice.getRoute().getDestination()}" /></p>
                                <p>Departure: <c:out value="${invoice.getRoute().getDeparture()}" /></p>
                                <p>Price: <c:out value="${invoice.getRoute().getPrice()}" /></p>
                                <p>Departure time: <c:out value="${invoice.getRoute().getDepartureTime()}" /></p>
                                <p>Creation time:
                                    <c:out value="${invoice.getRequest().getCreationTime()}" />
                                </p>
                                <c:if test="${sessionScope.user.isAdmin()}">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <button type="button" class="btn btn-success">Submit</button>
                                        </div>
                                        <div class="col-md-5"></div>
                                        <div class="col-md-3">
                                            <button type="button" class="btn btn-danger">Reject</button>
                                        </div>
                                        <div class="col-md-1"></div>
                                    </div>
                                    <p></p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>
