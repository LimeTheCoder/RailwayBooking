package com.limethecoder.dao.jdbc.mysql;


import com.limethecoder.dao.TrainDao;
import com.limethecoder.dao.exception.DaoException;
import com.limethecoder.dao.util.converter.ReadConverter;
import com.limethecoder.dao.util.converter.TrainReadConverter;
import com.limethecoder.entity.Train;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MySqlTrainDao extends AbstractDao<Train, String>
        implements TrainDao {

    private final static String SQL_SELECT_ALL =
            " SELECT * FROM Trains ";
    private final static String SQL_INSERT =
            "INSERT INTO Trains (serial_no, capacity) VALUES (?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Trains";
    private final static String SQL_UPDATE =
            "UPDATE Trains SET capacity = ? ";

    private final static String WHERE_SERIAL_NUMBER = " WHERE serial_no = ?";

    public MySqlTrainDao(Connection connection) {
        this(connection, new TrainReadConverter());
    }

    public MySqlTrainDao(Connection connection,
                         ReadConverter<Train> converter) {
        super(connection, converter);
    }

    @Override
    public Optional<Train> findOne(String serialNumber) {
        Objects.requireNonNull(serialNumber);

        return findOne(SQL_SELECT_ALL + WHERE_SERIAL_NUMBER, serialNumber);
    }

    @Override
    public List<Train> findAll() {
        return findAll(SQL_SELECT_ALL);
    }

    @Override
    public Train insert(Train train) {
        Objects.requireNonNull(train);

        executeUpdate(
                SQL_INSERT,
                train.getSerialNumber(),
                train.getCapacity()
        );

        return train;
    }

    @Override
    public void delete(String serialNumber) {
        Objects.requireNonNull(serialNumber);

        executeUpdate(SQL_DELETE + WHERE_SERIAL_NUMBER, serialNumber);
    }

    @Override
    public void update(Train train) {
        Objects.requireNonNull(train);

        executeUpdate(
                SQL_INSERT,
                train.getCapacity(),
                train.getSerialNumber()
        );
    }
}
