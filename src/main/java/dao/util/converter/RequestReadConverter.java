package dao.util.converter;


import dao.util.Util;
import entity.Request;
import entity.Station;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RequestReadConverter implements ReadConverter<Request> {
    private final static String DEPARTURE_PREFIX = "dep_";
    private final static String DESTINATION_PREFIX = "dest_";
    private final static String DEPARTURE_TIME_FIELD = "departure_time";
    private final static String CREATION_TIME_FIELD = "creation_time";
    private final static String ID_FIELD = "id";

    private final ReadConverter<User> userConverter;
    private final ReadConverter<Station> stationConverter;

    public RequestReadConverter() {
        this(new UserReadConverter(), new StationReadConverter());
    }

    public RequestReadConverter(ReadConverter<User> userConverter,
                                ReadConverter<Station> stationConverter) {
        this.userConverter = userConverter;
        this.stationConverter = stationConverter;
    }

    @Override
    public Request convertToObject(ResultSet resultSet, String prefix)
            throws SQLException {
        Station departureStation = stationConverter
                .convertToObject(resultSet, prefix + DEPARTURE_PREFIX);
        Station destinationStation = stationConverter
                .convertToObject(resultSet, prefix + DESTINATION_PREFIX);
        User passenger = userConverter.convertToObject(resultSet, prefix);
        return Request.newBuilder()
                .setDepartureTime(Util.toDate(
                        resultSet.getTimestamp(prefix + DEPARTURE_TIME_FIELD)))
                .setCreationTime(Util.toDate(
                        resultSet.getTimestamp(prefix + CREATION_TIME_FIELD)))
                .setDeparture(departureStation)
                .setDestination(destinationStation)
                .setPassenger(passenger)
                .setId(resultSet.getLong(prefix + ID_FIELD))
                .build();
    }
}
