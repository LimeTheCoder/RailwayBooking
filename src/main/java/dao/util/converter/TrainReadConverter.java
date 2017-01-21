package dao.util.converter;


import entity.Train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainReadConverter implements ReadConverter<Train> {
    private final static String SERIAL_NUMBER_FIELD = "serial_no";
    private final static String CAPACITY_FIELD = "capacity";

    @Override
    public List<Train> convertToList(ResultSet resultSet) throws SQLException {
        return convertToList(resultSet, "");
    }

    @Override
    public List<Train> convertToList(ResultSet resultSet, String prefix) throws SQLException {
        List<Train> trains = new ArrayList<>();

        while (resultSet.next()) {
            trains.add(convertToObject(resultSet, prefix));
        }

        return trains;
    }

    @Override
    public Train convertToObject(ResultSet resultSet) throws SQLException {
        return convertToObject(resultSet, "");
    }

    @Override
    public Train convertToObject(ResultSet resultSet, String prefix) throws SQLException {
        String serial = resultSet.getString(prefix + SERIAL_NUMBER_FIELD);
        int capacity = resultSet.getInt(prefix + CAPACITY_FIELD);
        return new Train(serial, capacity);
    }
}
