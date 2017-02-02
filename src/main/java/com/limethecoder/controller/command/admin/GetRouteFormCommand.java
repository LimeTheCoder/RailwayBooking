package com.limethecoder.controller.command.admin;

import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.entity.Station;
import com.limethecoder.entity.Train;
import com.limethecoder.service.Impl.StationServiceImpl;
import com.limethecoder.service.Impl.TrainServiceImpl;
import com.limethecoder.service.StationService;
import com.limethecoder.service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class GetRouteFormCommand implements Command {
    private StationService stationService = StationServiceImpl.getInstance();
    private TrainService trainService = TrainServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setStationsAttribute(request);
        setTrainsAttribute(request);

        return Views.ROUTE_CREATION;
    }

    private void setStationsAttribute(HttpServletRequest request) {
        List<Station> stations = stationService.findAll();
        request.setAttribute(Attributes.STATIONS_ATTR, stations);
    }

    private void setTrainsAttribute(HttpServletRequest request) {
        List<Train> trains = trainService.findAll();
        request.setAttribute(Attributes.TRAINS_ATTR, trains);
    }
}
