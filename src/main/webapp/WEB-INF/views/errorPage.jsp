<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="controller.constants.AttributesHolder" %>

<html>

<jsp:include page="/WEB-INF/views/snippets/header.jsp" />
<body>
<fmt:setLocale value='${sessionScope[AttributesHolder.LOCALE]}' />
<fmt:setBundle basename="messages"/>

<div class="alert alert-danger">
    <strong><fmt:message key="error" /></strong> <fmt:message key="invalid.page" />
</div>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>