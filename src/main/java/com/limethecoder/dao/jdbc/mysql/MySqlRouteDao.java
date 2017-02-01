package com.limethecoder.dao.jdbc.mysql;

import com.limethecoder.dao.RouteDao;
import com.limethecoder.dao.util.Util;
import com.limethecoder.dao.jdbc.mysql.converter.ReadConverter;
import com.limethecoder.dao.jdbc.mysql.converter.RouteReadConverter;
import com.limethecoder.entity.Route;
import com.limethecoder.entity.Station;

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
                    "and destination_time >= ?";

    private final JdbcDaoTemplate<Route> jdbcTemplate;

    public MySqlRouteDao(Connection connection) {
        this(connection, new RouteReadConverter());
    }

    public MySqlRouteDao(Connection connection,
                         ReadConverter<Route> converter) {
        jdbcTemplate = new JdbcDaoTemplate<>(connection, converter);
    }

    public MySqlRouteDao(JdbcDaoTemplate<Route> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Route> findOne(Long id) {
        return jdbcTemplate.findOne(SQL_SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Route> findAll() {
        return jdbcTemplate.findAll(SQL_SELECT_ALL);
    }

    @Override
    public Route insert(Route route) {
        Objects.requireNonNull(route);

        long generatedId = jdbcTemplate.executeInsertWithGeneratedPrimaryKey(
                SQL_INSERT,
                route.getDeparture().getId(),
                route.getDestination().getId(),
                Util.toTimestamp(route.getDepartureTime()),
                Util.toTimestamp(route.getDestinationTime()),
                route.getTrain().getSerialNumber(),
                route.getPrice()
        );

        route.setId(generatedId);

        return route;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.executeUpdate(SQL_DELETE + WHERE_ID, id);
    }

    @Override
    public void update(Route route) {
        Objects.requireNonNull(route);

        jdbcTemplate.executeUpdate(
                SQL_UPDATE + WHERE_ID,
                route.getDeparture().getId(),
                route.getDestination().getId(),
                Util.toTimestamp(route.getDepartureTime()),
                Util.toTimestamp(route.getDestinationTime()),
                route.getTrain().getSerialNumber(),
                route.getPrice(),
                route.getId()
        );
    }

    @Override
    public List<Route> findByStations(Station from, Station to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        return jdbcTemplate.findAll(
                SQL_SELECT_ALL + WHERE_DEP_AND_DEST_STATION_ID,
                from.getId(),
                to.getId()
        );
    }

    @Override
    public List<Route> findByStationsAndDate(Station from,
                                             Station to,
                                             Date after) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        Objects.requireNonNull(after);

        return jdbcTemplate.findAll(
                SQL_SELECT_ALL + WHERE_STATIONS_AND_DATE,
                from.getId(),
                to.getId(),
                Util.toTimestamp(after)
        );
    }
}
