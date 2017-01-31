package com.limethecoder.controller.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Specialize interface for command in front controller pattern.
 *
 * @see com.limethecoder.controller.FrontController
 * @author Taras Sakharchuk
 */
public interface Command {
    /** Return from execute in case when redirecting action happens */
    String REDIRECTED = "REDIRECTED";

    /**
     * Process request of user.
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    String execute(HttpServletRequest request,
                   HttpServletResponse response)
            throws ServletException, IOException;
}
