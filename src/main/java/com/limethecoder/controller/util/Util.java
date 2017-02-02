package com.limethecoder.controller.util;


import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Util {
    private final static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm";

    public static boolean isAlreadyLoggedIn(HttpSession session) {
        return session.getAttribute(Attributes.USER_ATTR) != null;
    }

    /**
     * Add next page to redirect
     *
     * @param request
     * @param response
     * @param pageToRedirect
     * @throws IOException
     */
    public static void redirectTo(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String pageToRedirect) throws IOException {
        response.sendRedirect(request.getServletPath() + pageToRedirect);
    }

    /**
     * Performs validation of given field with provided validator.
     * If error occurs add error message to list of errors.
     *
     * @param validator
     * @param field
     * @param errors
     * @param <T> type of field for validation
     */
    public static <T> void validateField(Validator<T> validator,
                                         T field,
                                         List<String> errors) {
        if(!validator.isValid(field)) {
            errors.add(validator.getErrorKey());
        }
    }

    public static Date parseDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

        try {
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Integer getIntegerFromRequest(HttpServletRequest request, String key) {
        try {
            return Integer.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return null;
        }
    }
}
