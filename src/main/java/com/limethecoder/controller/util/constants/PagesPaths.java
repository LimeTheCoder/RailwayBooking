package com.limethecoder.controller.util.constants;


public interface PagesPaths {
    String USER_PREFIX = "/user";
    String ADMIN_PREFIX = "/admin";

    String HOME_PATH = "/home";
    String LOGIN_PATH = "/login";
    String SIGN_UP_PATH = "/signup";
    String LOGOUT_PATH = "/logout";
    String ROUTES_PATH = "/routes";

    String REQUESTS_HISTORY_PATH = USER_PREFIX + "/history";
    String REQUEST_PATH = USER_PREFIX + "/request";
    String NEW_INVOICE_PATH = USER_PREFIX + "/invoices/new";
    String INVOICES_PATH = USER_PREFIX + "/invoices";
}
