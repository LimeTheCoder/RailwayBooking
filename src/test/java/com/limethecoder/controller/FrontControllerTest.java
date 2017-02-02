package com.limethecoder.controller;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.command.CommandHolder;
import com.limethecoder.controller.command.DefaultCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.limethecoder.controller.util.constants.PagesPaths.HOME_PATH;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FrontControllerTest {
    private final static String SOME_PAGE = "page";
    private final static String VALID_URI = "valid";
    private final static String INVALID_URI = "invalid";
    private final static String SERVLET_PATH = "path";

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private CommandHolder commandHolder;
    @Mock
    private HttpSession session;
    @Mock
    private Command redirectCommand;
    @Mock
    private Command forwardCommand;
    @Mock
    RequestDispatcher requestDispatcher;

    private FrontController controller;

    @Before
    public void setUp() throws IOException, ServletException {
        MockitoAnnotations.initMocks(this);
        controller = new FrontController();
        when(redirectCommand.execute(request, response)).thenReturn(Command.REDIRECTED);
        when(forwardCommand.execute(request, response)).thenReturn(SOME_PAGE);

        when(commandHolder.getCommand(VALID_URI, CommandHolder.Method.GET))
                .thenReturn(forwardCommand);
        when(commandHolder.getCommand(VALID_URI, CommandHolder.Method.POST))
                .thenReturn(redirectCommand);
        when(commandHolder.getCommand(eq(INVALID_URI), any())).thenReturn(new DefaultCommand());
        controller.setCommandHolder(commandHolder);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getServletPath()).thenReturn(SERVLET_PATH);
    }

    @Test
    public void testRedirectionToHomeWhenInvalidUri()
            throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(INVALID_URI);
        when(request.getMethod()).thenReturn("GET");

        controller.doGet(request, response);

        verify(response).sendRedirect(SERVLET_PATH + HOME_PATH);
        verify(requestDispatcher, never()).forward(request, response);
    }

    @Test
    public void testForwardingWhenValidUrlAndMethodGet()
            throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(VALID_URI);
        when(request.getMethod()).thenReturn("GET");

        controller.doGet(request, response);

        verify(response, never()).sendRedirect(any());
        verify(request).getRequestDispatcher(SOME_PAGE);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testRedirectionWhenValidUrlAndMethodPost()
            throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(VALID_URI);
        when(request.getMethod()).thenReturn("POST");

        controller.doGet(request, response);

        verify(request, never()).getRequestDispatcher(any());
        verify(requestDispatcher, never()).forward(request, response);
    }
}
