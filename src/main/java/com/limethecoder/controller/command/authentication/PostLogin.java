package com.limethecoder.controller.command.authentication;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.controller.validator.EmailValidator;
import com.limethecoder.controller.validator.PasswordValidator;
import com.limethecoder.entity.User;
import com.limethecoder.service.Impl.UserServiceImpl;
import com.limethecoder.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Command that process login POST request.
 */
public class PostLogin implements Command {
    private final static String EMAIL_PARAM = "email";
    private final static String PASSWORD_PARAM = "password";
    private final static String INVALID_CREDENTIALS = "invalid.credentials";

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        User userDto = getDataFromRequest(request);

        List<String> errors = validateData(userDto);

        if(errors.isEmpty()) {
            User user = loadUserFromDatabase(userDto.getEmail());
            addUserToSession(request.getSession(), user);

            Util.redirectTo(request, response, PagesPaths.HOME_PATH);

            return REDIRECTED;
        }

        addInvalidDataToRequest(request, userDto, errors);

        return Views.LOGIN_VIEW;
    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(EMAIL_PARAM))
                .setPassword(request.getParameter(PASSWORD_PARAM))
                .build();
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new EmailValidator(), user.getEmail(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);

        /* Check if entered password matches with user password only in case,
            when email and password is valid
        */
        if(errors.isEmpty() && !userService.
                isCredentialsValid(user.getEmail(), user.getPassword())) {
            errors.add(INVALID_CREDENTIALS);
        }

        return errors;
    }

    private User loadUserFromDatabase(String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional.orElseThrow(IllegalStateException::new);
    }

    private void addUserToSession(HttpSession session, User user)
            throws IOException {
        session.setAttribute(Attributes.USER_ATTR, user);
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         User user,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER_ATTR, user);
        request.setAttribute(Attributes.ERRORS_LIST, errors);
    }
}
