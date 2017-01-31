package com.limethecoder.controller.command.user.request;

import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.entity.Request;
import com.limethecoder.entity.Route;
import com.limethecoder.entity.Station;
import com.limethecoder.entity.User;
import com.limethecoder.service.Impl.RequestServiceImpl;
import com.limethecoder.service.Impl.RouteServiceImpl;
import com.limethecoder.service.Impl.StationServiceImpl;
import com.limethecoder.service.RequestService;
import com.limethecoder.service.RouteService;
import com.limethecoder.service.StationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class PostRequestForm implements Command {
    private final static String FROM_PARAM = "from";
    private final static String TO_PARAM = "to";
    private final static String DEP_DATE_PARAM = "dep_date";

    private final static String INVALID_DATE_KEY = "invalid.date";
    private final static String ERROR_EQUAL_STATION = "error.stations.equal";
    private final static String ERROR_DATE_BEFORE_CURRENT = "error.date.before";

    private final static String DATE_PATTERN = "yyyy-MM-dd";

    private final static String USER_REQUEST_PARAM = "request";

    private StationService stationService = StationServiceImpl.getInstance();
    private RouteService routeService = RouteServiceImpl.getInstance();
    private RequestService requestService = RequestServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {
        Request userRequestDto = getUserRequestFromHttpRequest(httpRequest);
        List<String> errors = validateData(userRequestDto);

        if(errors.isEmpty()) {
            List<Route> routes = loadRoutesWhichMatchRequest(userRequestDto);

            setUpUserRequestFields(userRequestDto, httpRequest, routes.size());
            saveUserRequestToDatabase(userRequestDto);
            setRequestAndRoutesAsSessionAttributes(
                    httpRequest,
                    userRequestDto,
                    routes
            );

            Util.redirectTo(httpRequest, response, PagesPaths.ROUTES_PATH);

            return REDIRECTED;
        }

        addInvalidDataToRequest(httpRequest, userRequestDto, errors);

        return Views.REQUEST_VIEW;
    }

    private Request getUserRequestFromHttpRequest(HttpServletRequest httpRequest) {
        long departureId = Long.valueOf(httpRequest.getParameter(FROM_PARAM));
        long destinationId = Long.valueOf(httpRequest.getParameter(TO_PARAM));
        String departureDate = httpRequest.getParameter(DEP_DATE_PARAM);

        return Request.newBuilder()
                .setDeparture(new Station(departureId))
                .setDestination(new Station(destinationId))
                .setDepartureTime(parseDate(departureDate))
                .build();
    }

    private Date parseDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

        try {
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            return null;
        }
    }

    private List<String> validateData(Request userRequest) {
        List<String> errors = new ArrayList<>();

        if(userRequest.getDeparture().equals(userRequest.getDestination())) {
            errors.add(ERROR_EQUAL_STATION);
        }

        if(userRequest.getDepartureTime() == null) {
            errors.add(INVALID_DATE_KEY);
        }

        Date current = new Date();

        if(userRequest.getDepartureTime().before(current)) {
            errors.add(ERROR_DATE_BEFORE_CURRENT);
        }

        return errors;
    }

    private List<Route> loadRoutesWhichMatchRequest(Request userRequest) {
        return routeService.findByStationsAndDate(
                userRequest.getDeparture(),
                userRequest.getDestination(),
                userRequest.getDepartureTime()
        );
    }

    private void setUpUserRequestFields(Request userRequest,
                                        HttpServletRequest httpRequest,
                                        int routesCnt) {
        User user = (User)httpRequest.getSession()
                .getAttribute(Attributes.USER_ATTR);
        userRequest.setPassenger(user);

        Optional<Station> departureOptional = stationService.findById(userRequest
                .getDeparture().getId());
        Optional<Station> destinationOptional = stationService.findById(userRequest
                .getDestination().getId());

        Station departure = departureOptional.orElse(userRequest.getDeparture());
        Station destination = destinationOptional.orElse(userRequest
                .getDestination());

        userRequest.setDeparture(departure);
        userRequest.setDestination(destination);
        userRequest.setResultCnt(routesCnt);
    }

    private void setRequestAndRoutesAsSessionAttributes(HttpServletRequest httpRequest,
                                                        Request userRequest,
                                                        List<Route> routes)
            throws IOException{
        httpRequest.getSession().setAttribute(Attributes.USER_REQUEST_ATTR,
                userRequest);
        httpRequest.getSession().setAttribute(Attributes.ROUTES_ATTR,
                routes);
    }

    private void addInvalidDataToRequest(HttpServletRequest httpRequest,
                                         Request userRequest,
                                         List<String> errors) {
        List<Station> stations = stationService.findAll();
        httpRequest.setAttribute(Attributes.STATIONS_ATTR, stations);

        httpRequest.setAttribute(Attributes.ERRORS_LIST, errors);
        httpRequest.setAttribute(USER_REQUEST_PARAM, userRequest);
    }

    private void saveUserRequestToDatabase(Request userRequest) {
        requestService.createRequest(userRequest);
    }
}
