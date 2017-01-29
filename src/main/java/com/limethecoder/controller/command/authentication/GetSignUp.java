package com.limethecoder.controller.command.authentication;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetSignUp implements Command {
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        return Views.SIGN_UP_VIEW;
    }
}
