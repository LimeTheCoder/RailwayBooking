package controller.command;


import controller.constants.Attributes;
import controller.constants.PagesPaths;
import controller.constants.Views;
import entity.User;
import org.apache.log4j.Logger;
import service.Impl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class PostLogin implements Command {
    private final static Logger logger = Logger
            .getLogger(PostLogin.class);

    private final static String EMAIL_PARAM = "email";
    private final static String PASSWORD_PARAM = "password";
    private final static String SEPARATOR = " : ";
    private final static String ERROR_MSG = "errorMsg";
    private final static String LOGIN_SUCCESS = "User login success with email ";
    private final static String LOGIN_FAILURE = "User login failure with email ";
    private final static String MESSAGE = "invalid.credentials";

    private final UserService userService = UserServiceImpl.getInstance();
    private User userDto;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if(isAlreadyLoggedIn(request.getSession())) {
            response.sendRedirect(request.getServletPath() + PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        userDto = getDataFromRequest(request);
        logger.debug(EMAIL_PARAM + SEPARATOR + userDto.getEmail());

        if(userService.isCredentialsValid(userDto.getEmail(),
                userDto.getPassword())) {
            onSuccess(request, response);
            return REDIRECTED;
        }

        onFailure(request);
        return Views.LOGIN_VIEW;
    }

    private boolean isAlreadyLoggedIn(HttpSession session) {
        return session.getAttribute(Attributes.USER_ATTR) != null;
    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(EMAIL_PARAM))
                .setPassword(request.getParameter(PASSWORD_PARAM))
                .build();
    }

    private void onSuccess(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {
        Optional<User> user = userService.findByEmail(userDto.getEmail());
        request.getSession().setAttribute(Attributes.USER_ATTR, user.get());
        response.sendRedirect(request.getServletPath() + PagesPaths.HOME_PATH);

        logger.debug(LOGIN_SUCCESS + userDto.getEmail());
    }

    private void onFailure(HttpServletRequest request) {
        request.setAttribute(Attributes.USER_ATTR, userDto);
        request.setAttribute(ERROR_MSG, MESSAGE);

        logger.debug(LOGIN_FAILURE + userDto.getEmail());
    }
}
