package com.limethecoder.controller.command;


import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.PagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Util.redirectTo(request, response, PagesPaths.HOME_PATH);
        return REDIRECTED;
    }
}
