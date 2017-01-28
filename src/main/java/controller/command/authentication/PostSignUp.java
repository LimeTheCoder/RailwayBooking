package controller.command.authentication;


import controller.command.Command;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import controller.util.validator.*;
import entity.User;
import org.apache.log4j.Logger;
import service.Impl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final static String ERRORS_LIST = "errors";
    private final static String INVALID_NAME_KEY = "invalid.name";
    private final static String INVALID_SURNAME_KEY = "invalid.surname";
    private final static String USER_ALREADY_EXISTS = "user.exists";

    private final static String ACCOUNT_CREATED =
            "Created account for user with email - ";

    private final UserService userService = UserServiceImpl.getInstance();

    private Validator<String> emailValidator = new EmailValidator();
    private Validator<String> passwordValidator = new PasswordValidator();
    private Validator<String> nameValidator = new NameValidator(INVALID_NAME_KEY);
    private Validator<String> surnameValidator =
            new NameValidator(INVALID_SURNAME_KEY);
    private Validator<String> phoneValidator = new PhoneValidator();

    private User userDto;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        userDto = getDataFromRequest(request);
        List<String> errors = validateData();

        if(errors.isEmpty()) {
            userService.createUser(userDto);
            addUserToSessionAndRedirect(request, response);
            logInfoAboutRegistration();
            return REDIRECTED;
        }

        addInvalidDataToRequest(request, errors);

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

    private List<String> validateData() {
        List<String> errors = new ArrayList<>();

        if(!emailValidator.isValid(userDto.getEmail())) {
            errors.add(emailValidator.getErrorKey());
        }

        if(!passwordValidator.isValid(userDto.getPassword())) {
            errors.add(passwordValidator.getErrorKey());
        }

        if(!phoneValidator.isValid(userDto.getPhone())) {
            errors.add(phoneValidator.getErrorKey());
        }

        if(!nameValidator.isValid(userDto.getName())) {
            errors.add(nameValidator.getErrorKey());
        }

        if(!surnameValidator.isValid(userDto.getSurname())) {
            errors.add(surnameValidator.getErrorKey());
        }

        if(errors.isEmpty() && userService.isUserExists(userDto.getEmail())) {
            errors.add(USER_ALREADY_EXISTS);
        }

        return errors;
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER_ATTR, userDto);
        request.setAttribute(ERRORS_LIST, errors);
    }

    private void addUserToSessionAndRedirect(HttpServletRequest request,
                                             HttpServletResponse response)
            throws IOException {
        request.getSession().setAttribute(Attributes.USER_ATTR, userDto);
        Util.redirectTo(request, response, PagesPaths.HOME_PATH);
    }

    private void logInfoAboutRegistration() {
        logger.info(ACCOUNT_CREATED +
                userDto.getEmail());
    }
}
