<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <head>
        <jsp:include page="/WEB-INF/views/snippets/header.jsp" />
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/resources/css/auth.css">
    </head>
</head>
<body>
    <jsp:include page="/WEB-INF/views/snippets/navbar.jsp" />

    <c:if test="${not empty requestScope.errors}">
        <div class="alert alert-danger">
            <c:forEach items="${requestScope.errors}" var="error" >
                <strong><fmt:message key="error" /></strong> <fmt:message key="${error}" /><br>
            </c:forEach>
        </div>
    </c:if>

    <div class="container">
        <div class="row main">
            <div class="panel-heading">
                <div class="panel-title text-center">
                    <h2 class="title"><fmt:message key="create.new.account" /></h2>
                    <hr />
                </div>
            </div>
            <div class="main-login main-center">
                <form class="form-horizontal" method="post">

                    <div class="form-group">
                        <label for="email" class="cols-sm-2 control-label"><fmt:message key="email" /></label>
                        <div class="cols-sm-10">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                <input type="text" class="form-control" name="email" id="email"
                                       placeholder="<fmt:message key="enter.email" />"
                                       value="<c:out value="${requestScope.user.getEmail()}" />" />
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="cols-sm-2 control-label"><fmt:message key="name" /></label>
                        <div class="cols-sm-10">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                <input type="text" class="form-control" name="name" id="name"
                                       placeholder="<fmt:message key="enter.name" />"
                                       value="<c:out value="${requestScope.user.getName()}" />" />
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="surname" class="cols-sm-2 control-label"><fmt:message key="surname" /></label>
                        <div class="cols-sm-10">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                <input type="text" class="form-control" name="surname" id="surname"
                                       placeholder="<fmt:message key="enter.surname" />"
                                       value="<c:out value="${requestScope.user.getSurname()}" />" />
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="surname" class="cols-sm-2 control-label"><fmt:message key="phone" /></label>
                        <div class="cols-sm-10">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-phone fa" aria-hidden="true"></i></span>
                                <input type="text" class="form-control" name="phone" id="phone"
                                       placeholder="<fmt:message key="enter.phone" />"
                                       value="<c:out value="${requestScope.user.getPhone()}" />" />
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="cols-sm-2 control-label"><fmt:message key="password" /></label>
                        <div class="cols-sm-10">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                <input type="password" class="form-control" name="password" id="password"
                                       placeholder="<fmt:message key="enter.password" />"/>
                            </div>
                        </div>
                    </div>

                    <input class="btn btn-primary btn-lg btn-block login-button" type="submit"
                           value="<fmt:message key="signup" />">
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>
