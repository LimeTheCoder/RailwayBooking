package com.limethecoder.controller.command.admin;

import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.dao.exception.DaoException;
import com.limethecoder.entity.Route;
import com.limethecoder.entity.Station;
import com.limethecoder.entity.Train;
import com.limethecoder.service.Impl.RouteServiceImpl;
import com.limethecoder.service.Impl.StationServiceImpl;
import com.limethecoder.service.Impl.TrainServiceImpl;
import com.limethecoder.service.RouteService;
import com.limethecoder.service.StationService;
import com.limethecoder.service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PostRouteCreation implements Command {
    private final static String FROM_PARAM = "from";
    private final static String TO_PARAM = "to";
    private final static String DEP_DATE_PARAM = "dep_date";
    private final static String DEST_DATE_PARAM = "dest_date";
    private final static String TRAIN_PARAM = "train";
    private final static String PRICE_PARAM = "price";

    private final static String INVALID_DATE_KEY = "invalid.date";
    private final static String ERROR_EQUAL_STATION = "error.stations.equal";
    private final static String ERROR_DATE_BEFORE_CURRENT = "error.date.before";
    private final static String INVALID_PRICE = "error.price";
    private final static String ERROR_TRAIN_IN_USE = "error.train";
    private final static String INVALID_DATE_RANGE = "invalid.date.range";

    private StationService stationService = StationServiceImpl.getInstance();
    private TrainService trainService = TrainServiceImpl.getInstance();
    private RouteService routeService = RouteServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Route routeDto = getRouteFromRequest(request);
        List<String> errors = validateData(routeDto);

        if(errors.isEmpty()) {
            boolean isSaved = saveRouteToDatabase(routeDto);

            if(isSaved) {
                Util.redirectTo(request, response, PagesPaths.ROUTES_PATH);
                return REDIRECTED;
            }

            errors.add(ERROR_TRAIN_IN_USE);
        }

        addInvalidDataToRequest(request, routeDto, errors);
        return Views.ROUTE_CREATION;
    }

    private Route getRouteFromRequest(HttpServletRequest request) {
        long departureId = Long.valueOf(request.getParameter(FROM_PARAM));
        long destinationId = Long.valueOf(request.getParameter(TO_PARAM));
        String trainNo = request.getParameter(TRAIN_PARAM);
        String departureDate = request.getParameter(DEP_DATE_PARAM);
        String destinationDate = request.getParameter(DEST_DATE_PARAM);
        Integer price = Util.getIntegerFromRequest(request, PRICE_PARAM);

        return Route.newBuilder()
                .setDeparture(new Station(departureId))
                .setDestination(new Station(destinationId))
                .setTrain(new Train(trainNo))
                .setDepartureTime(Util.parseDate(departureDate))
                .setDestinationTime(Util.parseDate(destinationDate))
                .setPrice(price)
                .setReservedCnt(0)
                .build();
    }

    private List<String> validateData(Route route) {
        List<String> errors = new ArrayList<>();

        Date departureTime = route.getDepartureTime();
        Date destinationTime = route.getDestinationTime();

        if(route.getDeparture().equals(route.getDestination())) {
            errors.add(ERROR_EQUAL_STATION);
        }

        if(departureTime  == null || destinationTime == null) {
            errors.add(INVALID_DATE_KEY);
        } else {

            Date current = new Date();

            if (departureTime.before(current)) {
                errors.add(ERROR_DATE_BEFORE_CURRENT);
            }

            if (departureTime.after(destinationTime)) {
                errors.add(INVALID_DATE_KEY);
            }

            if(errors.isEmpty() &&
                    isRouteTotalTimeValid(departureTime, destinationTime)) {
                errors.add(INVALID_DATE_RANGE);
            }
        }

        if(route.getPrice() == null || route.getPrice() < 0) {
            errors.add(INVALID_PRICE);
        }

        return errors;
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         Route route,
                                         List<String> errors) {
        List<Station> stations = stationService.findAll();
        request.setAttribute(Attributes.STATIONS_ATTR, stations);

        List<Train> trains = trainService.findAll();
        request.setAttribute(Attributes.TRAINS_ATTR, trains);

        request.setAttribute(Attributes.ROUTE_ATTR, route);
        request.setAttribute(Attributes.ERRORS_LIST, errors);
    }

    private boolean saveRouteToDatabase(Route route) {
        try {
            routeService.insert(route);
            return true;
        } catch (DaoException e) {
            return false;
        }
    }

    private boolean isRouteTotalTimeValid(Date departureTime, Date destinationTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(departureTime);
        c.add(Calendar.DATE, 2);

        return c.getTime().before(destinationTime);
    }

}
