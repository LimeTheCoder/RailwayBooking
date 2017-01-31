<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myLib" uri="/WEB-INF/customTags/requestedViewTag.tld"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>

<c:set var="homeView" scope="page" value="/WEB-INF/views/index.jsp" />
<c:set var="requestView" scope="page" value="/WEB-INF/views/createRequest.jsp" />
<c:set var="historyView" scope="page" value="/WEB-INF/views/requestHistory.jsp" />
<c:set var="routesView" scope="page" value="/WEB-INF/views/routes.jsp" />
<c:set var="loginView" scope="page" value="/WEB-INF/views/login.jsp" />
<c:set var="signUpView" scope="page" value="/WEB-INF/views/signup.jsp" />
<c:set var="invoicesView" scope="page" value="/WEB-INF/views/invoices.jsp" />

<c:set var="currView" scope="page">
    <myLib:viewUri/>
</c:set>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/site/home">RailWayBooking</a>
        </div>
        <ul class="nav navbar-nav">
            <c:choose>
                <c:when test="${homeView.equals(currView)}">
                    <li class="active">
                </c:when>
                <c:otherwise>
                    <li>
                </c:otherwise>
            </c:choose>
                <a href="${pageContext.request.contextPath}/site/home"><fmt:message key="home" /></a>
            </li>
            <c:choose>
            <c:when test="${routesView.equals(currView)}">
            <li class="active">
                </c:when>
                <c:otherwise>
            <li>
                </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/site/routes">
                    <fmt:message key="menu.routes" />
                </a>
            </li>
            <c:if test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
                <c:choose>
                    <c:when test="${invoicesView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/site/user/invoices">
                    <fmt:message key="menu.invoices" />
                </a>
                </li>

                <c:choose>
                    <c:when test="${requestView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/site/user/request">
                    <fmt:message key="menu.request" />
                </a>
            </li>

                <c:choose>
                    <c:when test="${historyView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/site/user/history">
                    <fmt:message key="menu.history" />
                </a>
            </li>
            </c:if>
            <c:if test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
                <c:choose>
                    <c:when test="${invoicesView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                    <a href="${pageContext.request.contextPath}/site/admin/invoices">
                        <fmt:message key="menu.invoices" />
                    </a>
                </li>
            </c:if>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-language fa-lg" aria-hidden="true"></i>
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
            <c:if test="${empty sessionScope.user}">
                <c:choose>
                    <c:when test="${signUpView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                    <a href="${pageContext.request.contextPath}/site/signup">
                        <span class="glyphicon glyphicon-user"></span><fmt:message key="signup" />
                    </a>
                </li>
                <c:choose>
                    <c:when test="${loginView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                    <a href="${pageContext.request.contextPath}/site/login">
                        <span class="glyphicon glyphicon-log-in"></span><fmt:message key="login" />
                    </a>
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <li>
                    <a href="#">
                        <fmt:message key="welcome" /><c:out value="${sessionScope.user.getName()}"/>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/site/logout">
                        <span class="glyphicon glyphicon-log-out"></span><fmt:message key="logout" />
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>