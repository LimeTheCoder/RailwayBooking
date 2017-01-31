package com.limethecoder.controller.command;


import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.entity.Request;
import com.limethecoder.entity.Route;
import com.limethecoder.service.Impl.RouteServiceImpl;
import com.limethecoder.service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetRoutes implements Command {
    private RouteService routeService = RouteServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {
        Request userRequest = getUserRequestFromSession(httpRequest.getSession());
        List<Route> routes = getRoutesFromSession(httpRequest.getSession());

        if(userRequest == null) {
            routes = routeService.findAll();
        }

        httpRequest.setAttribute(Attributes.ROUTES_ATTR, routes);

        return Views.ROUTES_VIEW;
    }

    @SuppressWarnings("unchecked")
    private List<Route> getRoutesFromSession(HttpSession session) {
        return (List<Route>) session.getAttribute(Attributes.ROUTES_ATTR);
    }

    private Request getUserRequestFromSession(HttpSession session) {
        return (Request) session
                .getAttribute(Attributes.USER_REQUEST_ATTR);
    }
}
