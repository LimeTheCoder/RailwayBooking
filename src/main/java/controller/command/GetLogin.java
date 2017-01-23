package controller.command;


import controller.constants.Attributes;
import controller.constants.PagesPaths;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static controller.constants.Views.LOGIN_VIEW;

public class GetLogin implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(isAlreadyLoggedIn(request.getSession())) {
            response.sendRedirect(request.getServletPath() + PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        request.setAttribute(Attributes.USER_ATTR, new User());
        return LOGIN_VIEW;
    }

    private boolean isAlreadyLoggedIn(HttpSession session) {
        return session.getAttribute(Attributes.USER_ATTR) != null;
    }
}
