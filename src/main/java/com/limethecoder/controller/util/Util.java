package com.limethecoder.controller.util;


import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class Util {

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
}
