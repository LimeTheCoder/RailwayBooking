package com.limethecoder.controller.command.authentication;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.controller.util.validator.*;
import com.limethecoder.entity.User;
import org.apache.log4j.Logger;
import com.limethecoder.service.Impl.UserServiceImpl;
import com.limethecoder.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostSignUp implements Command {
    private final static Logger logger = Logger
            .getLogger(PostSignUp.class);

    private final static String EMAIL_PARAM = "email";
    private final static String PASSWORD_PARAM = "password";
    private final static String NAME_PARAM = "name";
    private final static String SURNAME_PARAM = "surname";
    private final static String PHONE_PARAM = "phone";

    private final static String INVALID_NAME_KEY = "invalid.name";
    private final static String INVALID_SURNAME_KEY = "invalid.surname";
    private final static String USER_ALREADY_EXISTS = "user.exists";

    private final static String ACCOUNT_CREATED =
            "Created account for user with email - ";

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
            userService.createUser(userDto);
            addUserToSession(request.getSession(), userDto);
            logInfoAboutRegistration(userDto);

            Util.redirectTo(request, response, PagesPaths.HOME_PATH);

            return REDIRECTED;
        }

        addInvalidDataToRequest(request, userDto, errors);

        return Views.SIGN_UP_VIEW;
    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(EMAIL_PARAM))
                .setPassword(request.getParameter(PASSWORD_PARAM))
                .setName(request.getParameter(NAME_PARAM))
                .setSurname(request.getParameter(SURNAME_PARAM))
                .setPhone(request.getParameter(PHONE_PARAM))
                .build();
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new EmailValidator(), user.getEmail(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);
        Util.validateField(new PhoneValidator(), user.getPhone(), errors);

        NameValidator nameValidator = new NameValidator(INVALID_NAME_KEY);
        Util.validateField(nameValidator, user.getName(), errors);

        NameValidator surnameValidator = new NameValidator(INVALID_SURNAME_KEY);
        Util.validateField(surnameValidator, user.getSurname(), errors);

        if(errors.isEmpty() && userService.isUserExists(user.getEmail())) {
            errors.add(USER_ALREADY_EXISTS);
        }

        return errors;
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         User user,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER_ATTR, user);
        request.setAttribute(Attributes.ERRORS_LIST, errors);
    }

    private void addUserToSession(HttpSession session, User user)
            throws IOException {
        session.setAttribute(Attributes.USER_ATTR, user);
    }

    private void logInfoAboutRegistration(User user) {
        logger.info(ACCOUNT_CREATED + user.getEmail());
    }
}
