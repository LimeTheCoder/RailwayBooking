package com.limethecoder.controller.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.limethecoder.controller.util.constants.Views.HOME_VIEW;


public class GetHome implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return HOME_VIEW;
    }
}
