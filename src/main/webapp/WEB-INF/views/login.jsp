<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>
<html>
    <head>
        <jsp:include page="/WEB-INF/views/snippets/header.jsp" />

        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/resources/css/auth.css">
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
                    <h1 class="title"><fmt:message key="login" /></h1>
                    <hr />
                </div>
            </div>
            <div class="main-login main-center">
                <form class="form-horizontal" accept-charset="UTF-8" role="form" method="post">

                    <div class="form-group">
                        <label for="email" class="cols-sm-2 control-label"><fmt:message key="email" /></label>
                        <div class="cols-sm-10">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                <input type="text" class="form-control" name="email" id="email"
                                       placeholder="<fmt:message key="enter.email" />"
                                       value="${requestScope.user.getEmail()}"/>
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
                           value="<fmt:message key="login" />">
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>
</html>