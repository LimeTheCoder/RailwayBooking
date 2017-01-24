package controller.command;


import controller.util.Util;
import controller.util.constants.PagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command that logout current user from the site.
 */
public class Logout implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        Util.redirectTo(request, response, PagesPaths.LOGIN_PATH);
        return REDIRECTED;
    }
}
