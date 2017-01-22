package controller.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHomeCommand implements Command {
    private final static String HOME_PAGE =
            "/WEB-INF/views/index.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return HOME_PAGE;
    }
}
