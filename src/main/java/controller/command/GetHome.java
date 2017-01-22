package controller.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.constants.PathsHolder.HOME_VIEW;
import static controller.constants.PathsHolder.VIEWS_PATH;

public class GetHome implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return VIEWS_PATH + HOME_VIEW;
    }
}
