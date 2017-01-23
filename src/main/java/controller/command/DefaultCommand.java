package controller.command;


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultCommand implements Command {
    private final static Logger logger = Logger
            .getLogger(DefaultCommand.class);
    private final static String DEFAULT_PAGE = "/home";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Default command on " + request.getRequestURI());
        response.sendRedirect(request.getServletPath() + DEFAULT_PAGE);
        return REDIRECTED;
    }
}
