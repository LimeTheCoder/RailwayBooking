package dao.util.converter;


import dao.util.Util;
import entity.Route;
import entity.Station;
import entity.Train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteReadConverter implements ReadConverter<Route> {
    private final static String ID_FIELD = "id";
    private final static String DEPARTURE_TIME_FIELD = "departure_time";
    private final static String DESTINATION_TIME_FIELD = "destination_time";
    private final static String PRICE_FIELD = "price";
    private final static String RESERVED_CNT_FIELD = "reserved_cnt";

    private final static String TRAIN_PREFIX = "tr_";
    private final static String DEPARTURE_PREFIX = "dep_";
    private final static String DESTINATION_PREFIX = "dest_";

    private final ReadConverter<Station> stationConverter;
    private final ReadConverter<Train> trainConverter;

    public RouteReadConverter() {
        this(new StationReadConverter(), new TrainReadConverter());
    }

    public RouteReadConverter(ReadConverter<Station> stationConverter,
                              ReadConverter<Train> trainConverter) {
        this.stationConverter = stationConverter;
        this.trainConverter = trainConverter;
    }

    @Override
    public Route convertToObject(ResultSet resultSet, String prefix) throws SQLException {
        Station departureStation = stationConverter
                .convertToObject(resultSet, prefix + DEPARTURE_PREFIX);
        Station destinationStation = stationConverter
                .convertToObject(resultSet, prefix + DESTINATION_PREFIX);
        Train train = trainConverter.convertToObject(resultSet, prefix + TRAIN_PREFIX);

        return Route.newBuilder()
                .setId(resultSet.getLong(prefix + ID_FIELD))
                .setPrice(resultSet.getInt(prefix + PRICE_FIELD))
                .setDepartureTime(Util.toDate(
                        resultSet.getTimestamp(prefix + DEPARTURE_TIME_FIELD)))
                .setDestinationTime(Util.toDate(
                        resultSet.getTimestamp(prefix + DESTINATION_TIME_FIELD)))
                .setReservedCnt(resultSet.getInt(prefix + RESERVED_CNT_FIELD))
                .setDestination(destinationStation)
                .setDeparture(departureStation)
                .setTrain(train)
                .build();
    }
}
