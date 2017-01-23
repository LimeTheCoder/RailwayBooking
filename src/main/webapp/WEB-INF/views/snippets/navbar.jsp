<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/site/home">RailWayBooking</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active">
                <a href="${pageContext.request.contextPath}/site/home"><fmt:message key="home" /></a>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                ${sessionScope.locale.getLanguage().toUpperCase()}
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <c:forEach items="${applicationScope.supportedLocales}" var="lang" >
                    <li><a href="?lang=${lang}">${lang.toUpperCase()}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="${pageContext.request.contextPath}/site/signup">
                    <span class="glyphicon glyphicon-user"></span><fmt:message key="signup" />
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/site/login">
                    <span class="glyphicon glyphicon-log-in"></span><fmt:message key="login" />
                </a>
            </li>
        </ul>
    </div>
</nav>