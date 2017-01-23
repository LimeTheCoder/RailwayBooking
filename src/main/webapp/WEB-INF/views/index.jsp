<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp" />

<div class="jumbotron">
    <div class="container text-center">
        <h1><fmt:message key="jumbotron.title" /></h1>
    </div>
</div>

<div class="well"><fmt:message key="site.description" /></div>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>