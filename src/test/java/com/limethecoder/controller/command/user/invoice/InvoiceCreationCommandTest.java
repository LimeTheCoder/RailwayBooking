package com.limethecoder.controller.command.user.invoice;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.entity.Request;
import com.limethecoder.entity.Route;
import com.limethecoder.service.InvoiceService;
import com.limethecoder.service.RouteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static com.limethecoder.controller.util.constants.PagesPaths.HOME_PATH;
import static com.limethecoder.controller.util.constants.PagesPaths.REQUESTS_HISTORY_PATH;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class InvoiceCreationCommandTest {

    private final static String SERVLET_PATH = "path";
    private final static String ROUTE_PARAM = "route";

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private InvoiceService invoiceService;
    @Mock
    private RouteService routeService;

    private Command invoiceCreationCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");
        when(request.getServletPath()).thenReturn(SERVLET_PATH);

        invoiceCreationCommand = new InvoiceCreationCommand(routeService,
                invoiceService);
    }

    @Test
    public void testRedirectionToHomeWhenUserRequestNotInSession()
            throws IOException, ServletException {
        when(session.getAttribute(Attributes.USER_REQUEST_ATTR)).thenReturn(null);

        String result = invoiceCreationCommand.execute(request, response);
        assertEquals(Command.REDIRECTED, result);
        verify(response).sendRedirect(SERVLET_PATH + HOME_PATH);
    }

    @Test
    public void testRedirectionToHomeIfNoMatchingRouteForRequest()
            throws IOException, ServletException {
        when(session.getAttribute(Attributes.USER_REQUEST_ATTR))
                .thenReturn(new Request());
        Long routeId = 1L;
        when(request.getParameter(ROUTE_PARAM)).thenReturn(routeId.toString());
        when(routeService.findById(routeId)).thenReturn(Optional.empty());

        String result = invoiceCreationCommand.execute(request, response);
        assertEquals(Command.REDIRECTED, result);
        verify(response).sendRedirect(SERVLET_PATH + HOME_PATH);
    }

    @Test
    public void testRedirectionToRequestsHistoryPageWhenSuccess()
            throws IOException, ServletException {
        setSuccessBehaviour();

        String result = invoiceCreationCommand.execute(request, response);
        assertEquals(Command.REDIRECTED, result);
        verify(response).sendRedirect(SERVLET_PATH + REQUESTS_HISTORY_PATH);
    }

    @Test
    public void testCreationNewInvoiceWhenSuccess()
            throws IOException, ServletException {
        setSuccessBehaviour();

        invoiceCreationCommand.execute(request, response);

        verify(invoiceService).create(any());
    }

    @Test
    public void testRemoveAttributesFromSessionWhenSuccess()
            throws IOException, ServletException {

        setSuccessBehaviour();

        invoiceCreationCommand.execute(request, response);

        verify(session).removeAttribute(Attributes.USER_REQUEST_ATTR);
        verify(session).removeAttribute(Attributes.ROUTES_ATTR);
    }
    
    private void setSuccessBehaviour() {
        when(session.getAttribute(Attributes.USER_REQUEST_ATTR))
                .thenReturn(new Request());
        Long routeId = 1L;
        when(request.getParameter(ROUTE_PARAM)).thenReturn(routeId.toString());
        when(routeService.findById(routeId)).thenReturn(Optional.of(new Route()));
    }

}
