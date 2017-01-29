package controller.command.user.request;

import controller.command.Command;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import controller.util.validator.DateValidator;
import entity.Request;
import entity.Station;
import entity.User;
import service.Impl.StationServiceImpl;
import service.StationService;

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

    private DateValidator dateValidator = new DateValidator(DATE_PATTERN);
    private Request userRequestDto;

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        userRequestDto = getDataFromHttpRequest(httpRequest, errors);

        if(errors.isEmpty()) {
            setUpUserRequestFields(httpRequest);
            setAsAttributeAndRedirect(httpRequest, response);
            return REDIRECTED;
        }

        addInvalidDataToRequest(httpRequest, errors);

        return Views.REQUEST_VIEW;
    }

    private Request getDataFromHttpRequest(HttpServletRequest httpRequest, List<String> errors) {
        long departureId = Long.valueOf(httpRequest.getParameter(FROM_PARAM));
        long destinationId = Long.valueOf(httpRequest.getParameter(TO_PARAM));
        String departureDate = httpRequest.getParameter(DEP_DATE_PARAM);

        if(departureId == destinationId) {
            errors.add(ERROR_EQUAL_STATION);
        }

        if(!dateValidator.isValid(departureDate)) {
            errors.add(dateValidator.getErrorKey());
        }

        return Request.newBuilder()
                .setDeparture(new Station(departureId))
                .setDestination(new Station(destinationId))
                .setDepartureTime(dateValidator.getParsedDate())
                .build();
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
    }

    private void setAsAttributeAndRedirect(HttpServletRequest httpRequest,
                                         HttpServletResponse response)
            throws IOException{
        httpRequest.getSession().setAttribute(Attributes.USER_REQUEST_ATTR,
                userRequestDto);
        Util.redirectTo(httpRequest, response, PagesPaths.ROUTES_PATH);
    }

    private void addInvalidDataToRequest(HttpServletRequest httpRequest,
                                         List<String> errors) {
        List<Station> stations = stationService.findAll();
        httpRequest.setAttribute(Attributes.STATIONS_ATTR, stations);

        httpRequest.setAttribute(Attributes.ERRORS_LIST, errors);
        httpRequest.setAttribute(USER_REQUEST_PARAM, userRequestDto);
    }
}
