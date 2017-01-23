<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}' />
<fmt:setBundle basename="i18n.messages"/>
<html>
    <head>
        <jsp:include page="/WEB-INF/views/snippets/header.jsp" />
        <style>
            .container-style{
                padding-top:20px;
            }
        </style>
    </head>
<body>
<c:if test="${not empty requestScope.errorMsg}">
    <div class="alert alert-danger">
        <strong><fmt:message key="error" /></strong> <fmt:message key="${requestScope.errorMsg}" />
    </div>
</c:if>

    <div class="container container-style">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="login" /></h3>
                    </div>
                    <div class="panel-body">
                        <form accept-charset="UTF-8" role="form" method="POST">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="<fmt:message key="email" />"
                                           name="email" type="text" value="${requestScope.user.getEmail()}">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="<fmt:message key="password" />"
                                           name="password" type="password" value="">
                                </div>
                                <input class="btn btn-lg btn-success btn-block" type="submit" value="<fmt:message key="login" />">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/snippets/footer.jsp" />
</body>