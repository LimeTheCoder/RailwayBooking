package com.limethecoder.controller.util.filters;


import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.entity.Role;
import com.limethecoder.entity.User;
import com.sun.deploy.net.HttpRequest;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AuthFilter.class);
    private final static String ACCESS_DENIED = "Access denied for page: ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(!isUserLoggedIn(request)) {
            Util.redirectTo(
                    request,
                    (HttpServletResponse) servletResponse,
                    PagesPaths.LOGIN_PATH
            );
            logInfoAboutAccessDenied(request.getRequestURI());
            return;
        }

        User user = getUserFromSession(request.getSession());

        if(isUserRoleInvalidForRequestedPage(request, user)) {
            Util.redirectTo(
                    request,
                    (HttpServletResponse) servletResponse,
                    PagesPaths.HOME_PATH
            );
            logInfoAboutAccessDenied(request.getRequestURI());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}

    private boolean isUserLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute(Attributes.USER_ATTR) != null;
    }

    private User getUserFromSession(HttpSession session) {
        return (User)session.getAttribute(Attributes.USER_ATTR);
    }

    private boolean isUserRoleInvalidForRequestedPage(HttpServletRequest request,
                                                      User user) {
        return (isUserPage(request) && user.getRole() != Role.USER) ||
                (isAdminPage(request) && !user.isAdmin());
    }

    private boolean isUserPage(HttpServletRequest request) {
        return request
                .getRequestURI()
                .startsWith(PagesPaths.SITE_PREFIX + PagesPaths.USER_PREFIX);
    }

    private boolean isAdminPage(HttpServletRequest request) {
        return request
                .getRequestURI()
                .startsWith(PagesPaths.SITE_PREFIX + PagesPaths.ADMIN_PREFIX);
    }

    private void logInfoAboutAccessDenied(String uri) {
        logger.info(ACCESS_DENIED + uri);
    }
}
