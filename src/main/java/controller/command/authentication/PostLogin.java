package controller.command.authentication;


import controller.command.Command;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import controller.util.validator.EmailValidator;
import controller.util.validator.PasswordValidator;
import controller.util.validator.Validator;
import entity.User;
import service.Impl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final static String ERRORS_LIST = "errors";
    private final static String INVALID_CREDENTIALS = "invalid.credentials";

    private final UserService userService = UserServiceImpl.getInstance();
    private Validator<String> emailValidator = new EmailValidator();
    private Validator<String> passwordValidator = new PasswordValidator();
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
            addUserToSessionAndRedirect(request, response);
            return REDIRECTED;
        }

        addInvalidDataToRequest(request, errors);

        return Views.LOGIN_VIEW;
    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(EMAIL_PARAM))
                .setPassword(request.getParameter(PASSWORD_PARAM))
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

        /* Check if entered password matches with user password only in case,
            when email and password is valid
        */
        if(errors.isEmpty() && !userService.
                isCredentialsValid(userDto.getEmail(), userDto.getPassword())) {
            errors.add(INVALID_CREDENTIALS);
        }

        return errors;
    }

    private void addUserToSessionAndRedirect(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {
        Optional<User> user = userService.findByEmail(userDto.getEmail());
        request.getSession().setAttribute(Attributes.USER_ATTR, user.get());
        Util.redirectTo(request, response, PagesPaths.HOME_PATH);
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER_ATTR, userDto);
        request.setAttribute(ERRORS_LIST, errors);
    }
}
