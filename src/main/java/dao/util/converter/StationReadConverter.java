package dao.util.converter;


import entity.Station;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationReadConverter implements ReadConverter<Station> {
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String CITY_FIELD = "city";
    private final static String COUNTRY_FIELD = "country";

    @Override
    public List<Station> convertToList(ResultSet resultSet) throws SQLException {
        return convertToList(resultSet, "");
    }

    @Override
    public List<Station> convertToList(ResultSet resultSet, String prefix)
            throws SQLException {
        List<Station> stations = new ArrayList<>();

        while (resultSet.next()) {
            stations.add(convertToObject(resultSet, prefix));
        }

        return stations;
    }

    @Override
    public Station convertToObject(ResultSet resultSet) throws SQLException {
        return convertToObject(resultSet, "");
    }

    @Override
    public Station convertToObject(ResultSet resultSet, String prefix) throws SQLException {
        return Station.newBuilder()
                .setId(resultSet.getLong(prefix + ID_FIELD))
                .setCity(resultSet.getString(prefix + CITY_FIELD))
                .setCountry(resultSet.getString(prefix + COUNTRY_FIELD))
                .setName(resultSet.getString(prefix + NAME_FIELD))
                .build();
    }
}
