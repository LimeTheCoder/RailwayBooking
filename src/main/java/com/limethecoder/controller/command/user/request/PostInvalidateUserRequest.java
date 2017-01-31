package com.limethecoder.controller.command.user.request;

import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostInvalidateUserRequest implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        removeUserRequestAttributesFromSession(request);

        Util.redirectTo(request, response, PagesPaths.ROUTES_PATH);
        return REDIRECTED;
    }

    private void removeUserRequestAttributesFromSession(HttpServletRequest request) {
        request.getSession().removeAttribute(Attributes.USER_REQUEST_ATTR);
        request.getSession().removeAttribute(Attributes.ROUTES_ATTR);
    }
}
