<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp" />
    <style>
        hr{
            width: 10%;
            color: #fff;
        }
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
    <div class="panel-heading">
        <div class="panel-title text-center">
            <h1 class="title"><fmt:message key="request.history" /></h1>
            <hr />
        </div>
    </div>

    <div class="row col-md-10 col-md-offset-1 custyle">
        <table class="table table-striped custab">
            <thead>
            <tr>
                <th><fmt:message key="request.creation.time" /></th>
                <th><fmt:message key="request.from" /></th>
                <th><fmt:message key="request.to" /></th>
                <th><fmt:message key="request.departure.date" /></th>
                <th><fmt:message key="request.results.cnt" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="request" items="${requestScope.requests}">
                <tr>
                    <td><fmt:formatDate type="both" dateStyle="medium"
                                        value="${request.getCreationTime()}"/></td>
                    <td><c:out value="${request.getDeparture()}" /></td>
                    <td><c:out value="${request.getDestination()}" /></td>
                    <td><fmt:formatDate type="date" dateStyle="long"
                                        value="${request.getDepartureTime()}"/></td>
                    <td><c:out value="${request.getResultCnt()}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>


<jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>