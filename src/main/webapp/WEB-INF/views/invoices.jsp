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
                <h1 class="title"><fmt:message key="invoices.title" /></h1>
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
                                <fmt:message key="invoice.no" /><c:out value="${invoice.getId()}" />
                            </h3>
                            <div class='col-md-12'>
                                <p><fmt:message key="invoice.passenger" />: <c:out value="${invoice.getRequest().getPassenger()}" /></p>
                                <p><fmt:message key="invoice.status" />: <c:out value="${invoice.getStatus()}" /></p>
                                <p><fmt:message key="invoice.departure" />: <c:out value="${invoice.getRoute().getDeparture()}" /></p>
                                <p><fmt:message key="invoice.destination" />: <c:out value="${invoice.getRoute().getDestination()}" /></p>
                                <p><fmt:message key="route.price" />: <c:out value="${invoice.getRoute().getPrice()}" /></p>
                                <p><fmt:message key="invoice.departure.time" />:
                                    <fmt:formatDate type="both" dateStyle="medium"
                                                    value="${invoice.getRoute().getDepartureTime()}"/></p>
                                <p><fmt:message key="invoice.creation.time" />:
                                    <fmt:formatDate type="both" dateStyle="medium" value="${invoice.getRequest().getCreationTime()}" />
                                </p>
                                <c:if test="${sessionScope.user.isAdmin()}">
                                <form method="POST">
                                    <div class="row">
                                        <input type="hidden" name="invoice" value="${invoice.getId()}">
                                        <div class="col-md-3">
                                            <button type="submit" class="btn btn-success" name="submit">
                                                <fmt:message key="button.submit" />
                                            </button>
                                        </div>
                                        <div class="col-md-5"></div>
                                        <div class="col-md-3">
                                            <button type="submit" class="btn btn-danger" name="reject">
                                                <fmt:message key="button.reject" />
                                            </button>
                                        </div>
                                        <div class="col-md-1"></div>
                                    </div>
                                </form>
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
