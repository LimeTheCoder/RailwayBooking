package com.limethecoder.controller.command.user.request;

import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.controller.util.validator.DateValidator;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PostRequestForm implements Command {
    private final static String FROM_PARAM = "from";
    private final static String TO_PARAM = "to";
    private final static String DEP_DATE_PARAM = "dep_date";

    private final static String ERROR_EQUAL_STATION = "error.stations.equal";
    private final static String DATE_PATTERN = "yyyy-MM-dd";

    private final static String USER_REQUEST_PARAM = "request";

    private StationService stationService = StationServiceImpl.getInstance();
    private RouteService routeService = RouteServiceImpl.getInstance();
    private RequestService requestService = RequestServiceImpl.getInstance();

    private DateValidator dateValidator = new DateValidator(DATE_PATTERN);
    private Request userRequestDto;
    private List<Route> routes;

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = getUserRequestFromHttpRequest(httpRequest);

        if(errors.isEmpty()) {
            loadRoutesWhichMatchRequest();
            setUpUserRequestFields(httpRequest);
            saveUserRequest();
            setRequestAndRoutesAsSessionAttributes(httpRequest);
            Util.redirectTo(httpRequest, response, PagesPaths.ROUTES_PATH);

            return REDIRECTED;
        }

        addInvalidDataToRequest(httpRequest, errors);

        return Views.REQUEST_VIEW;
    }

    private List<String> getUserRequestFromHttpRequest(HttpServletRequest httpRequest) {
        long departureId = Long.valueOf(httpRequest.getParameter(FROM_PARAM));
        long destinationId = Long.valueOf(httpRequest.getParameter(TO_PARAM));
        String departureDate = httpRequest.getParameter(DEP_DATE_PARAM);

        List<String> errors = new ArrayList<>();

        if(departureId == destinationId) {
            errors.add(ERROR_EQUAL_STATION);
        }

        if(!dateValidator.isValid(departureDate)) {
            errors.add(dateValidator.getErrorKey());
        }

        userRequestDto = Request.newBuilder()
                .setDeparture(new Station(departureId))
                .setDestination(new Station(destinationId))
                .setDepartureTime(dateValidator.getParsedDate())
                .build();

        return errors;
    }

    private void loadRoutesWhichMatchRequest() {
        routes = routeService.findByStationsAndDate(userRequestDto.getDeparture(),
                userRequestDto.getDestination(), userRequestDto.getDepartureTime());
    }

    private void setUpUserRequestFields(HttpServletRequest httpRequest) {
        User user = (User)httpRequest.getSession()
                .getAttribute(Attributes.USER_ATTR);
        userRequestDto.setPassenger(user);

        Optional<Station> departure = stationService.findById(userRequestDto
                .getDeparture().getId());
        Optional<Station> destination = stationService.findById(userRequestDto
                .getDestination().getId());

        userRequestDto.setDeparture(departure.orElse(userRequestDto
                .getDeparture()));
        userRequestDto.setDestination(destination.orElse(userRequestDto
                .getDestination()));
        userRequestDto.setResultCnt(routes.size());
    }

    private void setRequestAndRoutesAsSessionAttributes(HttpServletRequest httpRequest)
            throws IOException{
        httpRequest.getSession().setAttribute(Attributes.USER_REQUEST_ATTR,
                userRequestDto);
        httpRequest.getSession().setAttribute(Attributes.ROUTES_ATTR,
                routes);
    }

    private void addInvalidDataToRequest(HttpServletRequest httpRequest,
                                         List<String> errors) {
        List<Station> stations = stationService.findAll();
        httpRequest.setAttribute(Attributes.STATIONS_ATTR, stations);

        httpRequest.setAttribute(Attributes.ERRORS_LIST, errors);
        httpRequest.setAttribute(USER_REQUEST_PARAM, userRequestDto);
    }

    private void saveUserRequest() {
        userRequestDto = requestService.createRequest(userRequestDto);
    }
}
