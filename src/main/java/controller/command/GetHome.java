package controller.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GetHome implements Command {

    private final static String HOME_VIEW = "/index.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return VIEWS_PATH + HOME_VIEW;
    }
}
