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
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Request userRequest = (Request) request.getSession()
                .getAttribute(Attributes.USER_REQUEST_ATTR);

        List<Route> routes;

        if(userRequest != null) {
            routes = routeService.findByStationsAndDate(userRequest.getDeparture(),
                    userRequest.getDestination(), userRequest.getDepartureTime());
            userRequest.setResultCnt(routes.size());
            userRequest = requestService.createRequest(userRequest);
            request.getSession().setAttribute(Attributes.USER_REQUEST_ATTR, userRequest);
        } else {
            routes = routeService.findAll();
        }

        request.setAttribute(ROUTES_PARAM, routes);

        return Views.ROUTES_VIEW;
    }
}
