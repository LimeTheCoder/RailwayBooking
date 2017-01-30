package com.limethecoder.controller.command;


import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.entity.Invoice;
import com.limethecoder.entity.Request;
import com.limethecoder.entity.Route;
import com.limethecoder.service.Impl.InvoiceServiceImpl;
import com.limethecoder.service.Impl.RouteServiceImpl;
import com.limethecoder.service.InvoiceService;
import com.limethecoder.service.RouteService;

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

    private Request userRequest;
    private Optional<Route> routeOptional;

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {

        if(isUserRequestInSession(httpRequest)) {
            getUserRequestAndRouteFromRequest(httpRequest);

            if(routeOptional.isPresent()) {
                createNewInvoice(routeOptional.get(), userRequest);
                removeAttributesFromSession(httpRequest.getSession());
                Util.redirectTo(httpRequest, response, PagesPaths.REQUESTS_HISTORY_PATH);

                return REDIRECTED;
            }
        }

        Util.redirectTo(httpRequest, response, PagesPaths.HOME_PATH);
        return REDIRECTED;
    }

    private void getUserRequestAndRouteFromRequest(HttpServletRequest httpRequest) {
        userRequest = (Request) httpRequest.getSession()
                .getAttribute(Attributes.USER_REQUEST_ATTR);
        long routeId = Long.valueOf(httpRequest.getParameter(ROUTE_PARAM));
        routeOptional = routeService.findById(routeId);
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

    private void removeAttributesFromSession(HttpSession session) {
        session.removeAttribute(Attributes.USER_REQUEST_ATTR);
        session.removeAttribute(Attributes.ROUTES_ATTR);
    }
}
