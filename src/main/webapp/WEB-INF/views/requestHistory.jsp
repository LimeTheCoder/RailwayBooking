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
        <table class="table table-striped custab">
            <thead>
            <tr>
                <th>Creation Time</th>
                <th>From</th>
                <th>To</th>
                <th>Departure Date</th>
                <th>Results count</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="request" items="${requestScope.requests}">
                <tr>
                    <td><fmt:formatDate type="both" dateStyle="medium"
                                        value="${request.getCreationTime()}"/></td>
                    <td>${request.getDeparture()}</td>
                    <td>${request.getDestination()}</td>
                    <td><fmt:formatDate type="date" dateStyle="long"
                                        value="${request.getDepartureTime()}"/></td>
                    <td>${request.getResultCnt()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>


<jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>