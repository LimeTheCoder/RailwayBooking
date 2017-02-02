package com.limethecoder.controller.util.constants;


public interface PagesPaths {
    String USER_PREFIX = "/user";
    String ADMIN_PREFIX = "/admin";

    String SITE_PREFIX = "/site";

    String HOME_PATH = "/home";
    String LOGIN_PATH = "/login";
    String SIGN_UP_PATH = "/signup";
    String LOGOUT_PATH = "/logout";
    String ROUTES_PATH = "/routes";

    String REQUESTS_HISTORY_PATH = USER_PREFIX + "/history";
    String REQUEST_PATH = USER_PREFIX + "/request";
    String REQUEST_INVALIDATE_PATH = USER_PREFIX + "/request/invalidate";
    String NEW_INVOICE_PATH = USER_PREFIX + "/invoices/new";
    String INVOICES_PATH = USER_PREFIX + "/invoices";

    String PENDING_INVOICES_PATH = ADMIN_PREFIX + "/invoices";
    String ROUTE_CREATION = ADMIN_PREFIX + "/route/creation";
}
