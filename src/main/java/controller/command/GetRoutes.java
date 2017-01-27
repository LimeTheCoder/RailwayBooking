package controller.command;


import controller.util.constants.Views;
import entity.Route;
import service.Impl.RouteServiceImpl;
import service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetRoutes implements Command {
    private final static String ROUTES_PARAM = "routes";

    private RouteService routeService = RouteServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Route> routes = routeService.findAll();
        request.setAttribute(ROUTES_PARAM, routes);
        return Views.ROUTES_VIEW;
    }
}
