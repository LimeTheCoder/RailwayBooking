package com.limethecoder.controller;

import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.command.CommandHolder;
import com.limethecoder.controller.command.CommandHolder.Method;
import com.limethecoder.controller.i18n.SupportedLocale;
import com.limethecoder.controller.util.constants.PagesPaths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Single handler for all kinds of requests coming to the application.
 *
 * @author Taras Sakharchuk
 */
public class FrontController extends HttpServlet {
    private final static String SUPPORTED_LOCALES = "supportedLocales";

    private CommandHolder commandHolder;

    @Override
    public void init() throws ServletException {
        super.init();
        commandHolder = new CommandHolder();
        getServletContext().setAttribute(SUPPORTED_LOCALES,
                SupportedLocale.getSupportedLanguages());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response, Method.GET);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response, Method.POST);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response,
                                Method method)
            throws ServletException, IOException {
        Command command = commandHolder.getCommand(getPath(request),
                method);

        String path = command.execute(request, response);
        if(!path.equals(Command.REDIRECTED)) {
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    private String getPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.replaceAll(PagesPaths.SITE_PREFIX, "");
    }

    void setCommandHolder(CommandHolder commandHolder) {
        this.commandHolder = commandHolder;
    }
}
