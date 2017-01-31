package com.limethecoder.controller.command.user.request;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.entity.Request;
import com.limethecoder.entity.User;
import com.limethecoder.service.Impl.RequestServiceImpl;
import com.limethecoder.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetRequestsHistory implements Command {
    private final static String REQUESTS_ATTR = "requests";
    private RequestService requestService = RequestServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)httpRequest.getSession()
                .getAttribute(Attributes.USER_ATTR);

        List<Request> userRequests = requestService
                .findAllByPassenger(user.getEmail());
        httpRequest.setAttribute(REQUESTS_ATTR, userRequests);

        return Views.REQUESTS_HISTORY_VIEW;
    }
}
