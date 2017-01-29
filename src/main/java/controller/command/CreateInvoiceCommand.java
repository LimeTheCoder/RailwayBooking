package controller.command;


import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import entity.Invoice;
import entity.Request;
import entity.Route;
import service.Impl.InvoiceServiceImpl;
import service.Impl.RouteServiceImpl;
import service.InvoiceService;
import service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class CreateInvoiceCommand implements Command {
    private final static String ROUTE_PARAM = "route";

    private RouteService routeService = RouteServiceImpl.getInstance();
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {

        if(isUserRequestInSession(httpRequest)) {
            Request userRequest = (Request) httpRequest.getSession()
                    .getAttribute(Attributes.USER_REQUEST_ATTR);
            long routeId = Long.valueOf(httpRequest.getParameter(ROUTE_PARAM));
            Optional<Route> route = routeService.findById(routeId);

            if(route.isPresent()) {
                createNewInvoice(route.get(), userRequest);
                removeUserRequestFromSession(httpRequest.getSession());
                Util.redirectTo(httpRequest, response, PagesPaths.REQUESTS_HISTORY_PATH);

                return REDIRECTED;
            }
        }

        Util.redirectTo(httpRequest, response, PagesPaths.HOME_PATH);
        return REDIRECTED;
    }

    private boolean isUserRequestInSession(HttpServletRequest request) {
        return request.getSession()
                .getAttribute(Attributes.USER_REQUEST_ATTR) != null;
    }

    private void createNewInvoice(Route route, Request userRequest) {
        Invoice invoice = Invoice.newBuilder()
                .setRequest(userRequest)
                .setRoute(route)
                .setStatus(Invoice.Status.PENDING)
                .build();
        invoiceService.create(invoice);
    }

    private void removeUserRequestFromSession(HttpSession session) {
        session.removeAttribute(Attributes.USER_REQUEST_ATTR);
    }
}
