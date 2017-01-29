package com.limethecoder.controller.command.user.request;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.entity.Station;
import com.limethecoder.service.Impl.StationServiceImpl;
import com.limethecoder.service.StationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetRequestForm implements Command {
    private StationService stationService = StationServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Station> stations = stationService.findAll();
        request.setAttribute(Attributes.STATIONS_ATTR, stations);
        return Views.REQUEST_VIEW;
    }
}
