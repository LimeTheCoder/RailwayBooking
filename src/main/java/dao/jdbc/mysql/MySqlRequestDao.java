package dao.jdbc.mysql;


import dao.RequestDao;
import dao.exception.DaoException;
import dao.util.Util;
import dao.util.converter.ReadConverter;
import dao.util.converter.RequestReadConverter;
import entity.Request;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlRequestDao implements RequestDao {
    private final static String SQL_SELECT_ALL =
            "SELECT r.departure, r.destination, r.departure_time, " +
                    "r.creation_time, r.id, r.passenger" +
                    "s1.name AS dep_name, s1.city as dep_city, " +
                    "s1.country as dep_country, s1.id as dep_id, " +
                    "s2.name AS dest_name, s2.city as dest_city, " +
                    "s2.country as dest_country, s2.id as dest_id, " +
                    "u.email, u.name, u.surname, u.password, u.phone, u.role " +
                    "FROM Requests AS r " +
                    "JOIN Stations AS s1 ON r.departure = s1.id " +
                    "JOIN Stations AS s2 ON r.destination = s2.id " +
                    "JOIN Users as u ON r.passenger = u.email";

    private final static String SQL_INSERT =
            "INSERT INTO Requests (departure, destination, " +
                    "departure_time, passenger) " +
                    "VALUES (?,?,?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Requests";
    private final static String SQL_UPDATE =
            "UPDATE Requests SET departure = ?, destination = ?, " +
                    "departure_time = ?, passenger = ?";

    private final static String WHERE_ID = " WHERE id = ?";
    private final static String WHERE_PASSENGER = "WHERE passenger = ?";

    private final Connection connection;
    private final ReadConverter<Request> converter;

    public MySqlRequestDao(Connection connection) {
        this(connection, new RequestReadConverter());
    }

    public MySqlRequestDao(Connection connection, ReadConverter<Request> converter) {
        this.connection = connection;
        this.converter = converter;
    }

    @Override
    public Optional<Request> findOne(Long id) {
        Objects.requireNonNull(id);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL + WHERE_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Request> requests = converter.convertToList(resultSet);
                return Optional.ofNullable(requests.isEmpty() ? null : requests.get(0));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Request> findAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {
            return converter.convertToList(resultSet);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isExist(Long id) {
        return findOne(id).isPresent();
    }

    @Override
    public Request insert(Request request) {
        Objects.requireNonNull(request);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT)) {

            prepareStatement(statement, request);

            long id = statement.executeUpdate();
            request.setId(id);

            return request;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_DELETE + WHERE_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Request request) {
        Objects.requireNonNull(request);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_UPDATE + WHERE_ID)) {

            prepareStatement(statement, request);

            statement.setLong(5, request.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Request> findAllByPassenger(Long passengerId) {
        Objects.requireNonNull(passengerId);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL + WHERE_PASSENGER)) {

            statement.setLong(1, passengerId);
            ResultSet resultSet = statement.executeQuery();

            return converter.convertToList(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void prepareStatement(PreparedStatement statement, Request request)
            throws SQLException {
        statement.setLong(1, request.getDeparture().getId());
        statement.setLong(2, request.getDestination().getId());
        statement.setTimestamp(3, Util.toTimestamp(request.getDepartureTime()));
        statement.setString(4, request.getPassenger().getEmail());
    }
}
