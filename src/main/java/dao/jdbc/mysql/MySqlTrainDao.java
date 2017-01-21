package dao.jdbc.mysql;


import dao.TrainDao;
import dao.exception.DaoException;
import dao.util.converter.ReadConverter;
import dao.util.converter.TrainReadConverter;
import entity.Train;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MySqlTrainDao implements TrainDao {
    private final static String SQL_SELECT_ALL =
            " SELECT * FROM Trains ";
    private final static String SQL_INSERT =
            "INSERT INTO Trains (serial_no, capacity) VALUES (?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Trains";
    private final static String SQL_UPDATE =
            "UPDATE Trains SET capacity = ? ";

    private final static String WHERE_SERIAL_NUMBER = " WHERE serial_no = ?";

    private final Connection connection;
    private final ReadConverter<Train> converter;

    public MySqlTrainDao(Connection connection) {
        this(connection, new TrainReadConverter());
    }

    public MySqlTrainDao(Connection connection, ReadConverter<Train> converter) {
        this.connection = connection;
        this.converter = converter;
    }

    @Override
    public Optional<Train> findOne(String serialNumber) {
        Objects.requireNonNull(serialNumber);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL + WHERE_SERIAL_NUMBER)) {

            statement.setString(1, serialNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Train> userList = converter.convertToList(resultSet);
                return Optional.ofNullable(userList.isEmpty() ? null : userList.get(0));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Train> findAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {
            return converter.convertToList(resultSet);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isExist(String serialNumber) {
        return findOne(serialNumber).isPresent();
    }

    @Override
    public Train insert(Train train) {
        Objects.requireNonNull(train);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT)) {

            statement.setString(1, train.getSerialNumber());
            statement.setInt(2, train.getCapacity());
            statement.executeUpdate();
            return train;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(String serialNumber) {
        Objects.requireNonNull(serialNumber);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_DELETE + WHERE_SERIAL_NUMBER)) {
            statement.setString(1, serialNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Train train) {
        Objects.requireNonNull(train);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_UPDATE + WHERE_SERIAL_NUMBER)) {

            statement.setInt(1, train.getCapacity());
            statement.setString(2, train.getSerialNumber());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
