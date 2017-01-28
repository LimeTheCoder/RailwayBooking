package controller;

import controller.command.Command;
import controller.command.CommandHolder;
import controller.command.CommandHolder.Method;
import controller.i18n.SupportedLocale;
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

    private final static Logger logger = Logger
            .getLogger(FrontController.class);
    private final static String PREFIX = ".*/site";
    private final static String REQUESTED_PATH = "Requested path: ";
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

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        Command command = commandHolder.getCommand(getPath(request),
                getMethod(request));

        String path = command.execute(request, response);
        if(!path.equals(Command.REDIRECTED)) {
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    private Method getMethod(HttpServletRequest request) {
        return Method.valueOf(request.getMethod());
    }

    private String getPath(HttpServletRequest request) {
        return request.getRequestURI().replaceAll(PREFIX, "");
    }
}
