package com.limethecoder.dao.jdbc.mysql.converter;


import com.limethecoder.entity.Station;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StationReadConverter implements ReadConverter<Station> {
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String CITY_FIELD = "city";
    private final static String COUNTRY_FIELD = "country";

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
