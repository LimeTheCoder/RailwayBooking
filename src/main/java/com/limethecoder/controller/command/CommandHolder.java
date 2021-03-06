package com.limethecoder.controller.command;


import com.limethecoder.controller.command.admin.GetPendingInvoices;
import com.limethecoder.controller.command.admin.GetRouteFormCommand;
import com.limethecoder.controller.command.admin.PostHandleUserInvoice;
import com.limethecoder.controller.command.admin.PostRouteCreation;
import com.limethecoder.controller.command.authentication.*;
import com.limethecoder.controller.command.user.invoice.InvoiceCreationCommand;
import com.limethecoder.controller.command.user.invoice.GetInvoices;
import com.limethecoder.controller.command.user.request.GetRequestForm;
import com.limethecoder.controller.command.user.request.GetRequestsHistory;
import com.limethecoder.controller.command.user.request.PostInvalidateUserRequest;
import com.limethecoder.controller.command.user.request.PostRequestForm;
import com.limethecoder.controller.util.constants.PagesPaths;

import java.util.HashMap;
import java.util.Map;


public class CommandHolder {
    public enum Method {
        GET, POST
    }

    private final static String DELIMITER = ":";

    private final Command DEFAULT_COMMAND = new DefaultCommand();

    private Map<String, Command> commands = new HashMap<>();

    public CommandHolder() {
        init();
    }

    private void init() {
        commands.put(buildKey(PagesPaths.HOME_PATH, Method.GET),
                new GetHome());
        commands.put(buildKey(PagesPaths.LOGIN_PATH, Method.GET),
                new GetLogin());
        commands.put(buildKey(PagesPaths.LOGIN_PATH, Method.POST),
                new PostLogin());
        commands.put(buildKey(PagesPaths.SIGN_UP_PATH, Method.GET),
                new GetSignUp());
        commands.put(buildKey(PagesPaths.SIGN_UP_PATH, Method.POST),
                new PostSignUp());
        commands.put(buildKey(PagesPaths.LOGOUT_PATH, Method.GET),
                new Logout());
        commands.put(buildKey(PagesPaths.REQUESTS_HISTORY_PATH, Method.GET),
                new GetRequestsHistory());
        commands.put(buildKey(PagesPaths.REQUEST_PATH, Method.GET),
                new GetRequestForm());
        commands.put(buildKey(PagesPaths.REQUEST_PATH, Method.POST),
                new PostRequestForm());
        commands.put(buildKey(PagesPaths.ROUTES_PATH, Method.GET),
                new GetRoutes());
        commands.put(buildKey(PagesPaths.REQUEST_INVALIDATE_PATH, Method.POST),
                new PostInvalidateUserRequest());
        commands.put(buildKey(PagesPaths.NEW_INVOICE_PATH, Method.POST),
                new InvoiceCreationCommand());
        commands.put(buildKey(PagesPaths.INVOICES_PATH, Method.GET),
                new GetInvoices());
        commands.put(buildKey(PagesPaths.PENDING_INVOICES_PATH, Method.GET),
                new GetPendingInvoices());
        commands.put(buildKey(PagesPaths.PENDING_INVOICES_PATH, Method.POST),
                new PostHandleUserInvoice());
        commands.put(buildKey(PagesPaths.ROUTE_CREATION, Method.GET),
                new GetRouteFormCommand());
        commands.put(buildKey(PagesPaths.ROUTE_CREATION, Method.POST),
                new PostRouteCreation());
    }

    public Command getCommand(String path, Method method) {
        return commands.getOrDefault(buildKey(path, method), DEFAULT_COMMAND);
    }

    private String buildKey(String path, Method method) {
        return method.name() + DELIMITER + path;
    }
}