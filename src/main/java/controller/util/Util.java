package controller.util;


import controller.util.constants.Attributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Util {

    public static boolean isAlreadyLoggedIn(HttpSession session) {
        return session.getAttribute(Attributes.USER_ATTR) != null;
    }

    /**
     * Add next page to redirect
     *
     * @param request
     * @param response
     * @param pageToRedirect
     * @throws IOException
     */
    public static void redirectTo(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String pageToRedirect) throws IOException {
        response.sendRedirect(request.getServletPath() + pageToRedirect);
    }
}
