package com.limethecoder.controller.command;


import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.PagesPaths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultCommand implements Command {
    private final static Logger logger = Logger
            .getLogger(DefaultCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Default command on " + request.getRequestURI());
        Util.redirectTo(request, response, PagesPaths.HOME_PATH);
        return REDIRECTED;
    }
}
