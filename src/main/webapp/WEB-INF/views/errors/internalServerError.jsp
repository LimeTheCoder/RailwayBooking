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

<div class="alert alert-danger">
    <strong><fmt:message key="error" /></strong> <fmt:message key="internal.error" />
</div>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>