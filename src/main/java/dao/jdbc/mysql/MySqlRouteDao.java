package dao.jdbc.mysql;

import dao.RouteDao;
import dao.exception.DaoException;
import dao.util.Util;
import dao.util.converter.ReadConverter;
import dao.util.converter.RouteReadConverter;
import entity.Route;
import entity.Station;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class MySqlRouteDao implements RouteDao {
    private final static String SQL_SELECT_ALL =
            "SELECT Routes.departure_station, Routes.destination_station, " +
                    "Routes.departure_time, Routes.destination_time, " +
                    "Routes.price, Routes.train, Routes.reserved_cnt, Routes.id, " +
                    "s1.name AS dep_name, s1.city as dep_city, " +
                    "s1.country as dep_country, s1.id as dep_id, " +
                    "s2.name AS dest_name, s2.city as dest_city, " +
                    "s2.country as dest_country, s2.id as dest_id, " +
                    "t1.serial_no as tr_serial_no, t1.capacity as tr_capacity " +
                    "FROM Routes " +
                    "JOIN Stations AS s1 ON Routes.departure_station = s1.id " +
                    "JOIN Stations AS s2 ON Routes.destination_station = s2.id " +
                    "JOIN Trains as t1 ON Routes.train = t1.serial_no";

    private final static String SQL_INSERT =
            "INSERT INTO Routes (departure_station, destination_station, " +
                    "departure_time, destination_time, train, price) " +
                    "VALUES (?,?,?,?,?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Routes";
    private final static String SQL_UPDATE =
            "UPDATE Routes SET departure_station = ?, destination_station = ?, " +
                    "departure_time = ?, destination_time = ?, " +
                    "train = ?, price = ? ";

    private final static String WHERE_ID = " WHERE Routes.id = ?";
    private final static String WHERE_DEP_AND_DEST_STATION_ID =
            " WHERE departure_station = ? and destination_station = ?";
    private final static String WHERE_STATIONS_AND_DATE =
            " WHERE departure_station = ? and destination_station = ? " +
                    "and destination_time > ?";

    private final Connection connection;
    private final ReadConverter<Route> converter;

    public MySqlRouteDao(Connection connection) {
        this(connection, new RouteReadConverter());
    }

    public MySqlRouteDao(Connection connection, ReadConverter<Route> converter) {
        this.connection = connection;
        this.converter = converter;
    }

    @Override
    public Optional<Route> findOne(Long id) {
        Objects.requireNonNull(id);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL + WHERE_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Route> routes = converter.convertToList(resultSet);
                return Optional.ofNullable(routes.isEmpty() ? null : routes.get(0));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Route> findAll() {
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
    public Route insert(Route route) {
        Objects.requireNonNull(route);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT)) {

            prepareStatement(statement, route);

            long id = statement.executeUpdate();
            route.setId(id);

            return route;

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
    public void update(Route route) {
        Objects.requireNonNull(route);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_UPDATE + WHERE_ID)) {

            prepareStatement(statement, route);
            statement.setLong(7, route.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Route> findByStations(Station from, Station to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL +
                        WHERE_DEP_AND_DEST_STATION_ID)) {

            statement.setLong(1, from.getId());
            statement.setLong(2, to.getId());

            ResultSet resultSet = statement.executeQuery();
            return converter.convertToList(resultSet);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Route> findByStationsAndDate(Station from,
                                             Station to,
                                             Date after) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        Objects.requireNonNull(after);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL +
                        WHERE_STATIONS_AND_DATE)) {

            statement.setLong(1, from.getId());
            statement.setLong(2, to.getId());
            statement.setTimestamp(3, Util.toTimestamp(after));

            ResultSet resultSet = statement.executeQuery();
            return converter.convertToList(resultSet);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void prepareStatement(PreparedStatement statement, Route route)
            throws SQLException {
        statement.setLong(1, route.getDeparture().getId());
        statement.setLong(2, route.getDestination().getId());
        statement.setTimestamp(3, Util.toTimestamp(
                route.getDepartureTime())
        );
        statement.setTimestamp(4, Util.toTimestamp(
                route.getDestinationTime())
        );
        statement.setString(5, route.getTrain().getSerialNumber());
        statement.setInt(6, route.getPrice());
    }
}
