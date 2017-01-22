package controller;

import controller.command.Command;
import controller.command.CommandHolder;
import controller.command.CommandHolder.Method;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FrontController extends HttpServlet {

    private static final Logger logger = Logger
            .getLogger(FrontController.class);

    private CommandHolder commandHolder;

    @Override
    public void init() throws ServletException {
        super.init();
        commandHolder = new CommandHolder();
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

        logger.debug("Requested path: " + getPath(request));

        String view = command.execute(request, response);
        request.getRequestDispatcher(view).forward(request, response);
    }

    private Method getMethod(HttpServletRequest request) {
        return Method.valueOf(request.getMethod());
    }

    private String getPath(HttpServletRequest request) {
        return request.getRequestURI()
                .substring(request.getContextPath().length());
    }
}
