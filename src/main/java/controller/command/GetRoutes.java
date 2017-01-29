package controller.command;


import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.Request;
import entity.Route;
import service.Impl.RequestServiceImpl;
import service.Impl.RouteServiceImpl;
import service.RequestService;
import service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetRoutes implements Command {
    private final static String ROUTES_PARAM = "routes";

    private RouteService routeService = RouteServiceImpl.getInstance();
    private RequestService requestService = RequestServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {
        Request userRequest = (Request) httpRequest.getSession()
                .getAttribute(Attributes.USER_REQUEST_ATTR);

        List<Route> routes;

        if(userRequest != null) {
            routes = routeService.findByStationsAndDate(userRequest.getDeparture(),
                    userRequest.getDestination(), userRequest.getDepartureTime());
            userRequest.setResultCnt(routes.size());
            saveUserRequestAndAddToSession(userRequest, httpRequest);
        } else {
            routes = routeService.findAll();
        }

        httpRequest.setAttribute(ROUTES_PARAM, routes);

        return Views.ROUTES_VIEW;
    }

    private void saveUserRequestAndAddToSession(Request userRequest,
                                                HttpServletRequest httpRequest) {
        userRequest = requestService.createRequest(userRequest);
        httpRequest.getSession().setAttribute(Attributes.USER_REQUEST_ATTR, userRequest);
    }
}
