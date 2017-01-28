package controller.command;


import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import entity.Request;
import entity.User;
import service.Impl.RequestServiceImpl;
import service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetRequestsHistory implements Command {
    private final static String REQUESTS_ATTR = "requests";
    private RequestService requestService = RequestServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession()
                .getAttribute(Attributes.USER_ATTR);

        if(user == null) {
            Util.redirectTo(request, response, PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        List<Request> userRequests = requestService
                .findAllByPassenger(user.getEmail());
        request.setAttribute(REQUESTS_ATTR, userRequests);

        return Views.REQUESTS_HISTORY_VIEW;
    }
}
