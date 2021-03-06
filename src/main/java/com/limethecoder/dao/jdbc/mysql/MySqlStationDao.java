package com.limethecoder.dao.jdbc.mysql;


import com.limethecoder.dao.StationDao;
import com.limethecoder.dao.jdbc.mysql.converter.ReadConverter;
import com.limethecoder.dao.jdbc.mysql.converter.StationReadConverter;
import com.limethecoder.entity.Station;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlStationDao implements StationDao {

    private final static String SQL_SELECT_ALL =
            " SELECT * FROM Stations ";
    private final static String SQL_INSERT =
            "INSERT INTO Stations (name, city, country) VALUES (?,?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Stations";
    private final static String SQL_UPDATE =
            "UPDATE Stations SET name = ?, city = ?, country = ? ";

    private final static String WHERE_ID = " WHERE id = ?";

    private final JdbcDaoTemplate<Station> jdbcTemplate;

    public MySqlStationDao(Connection connection) {
        this(connection, new StationReadConverter());
    }

    public MySqlStationDao(Connection connection,
                           ReadConverter<Station> converter) {
        jdbcTemplate = new JdbcDaoTemplate<>(connection, converter);
    }

    public MySqlStationDao(JdbcDaoTemplate<Station> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Station> findOne(Long id) {
        return jdbcTemplate.findOne(SQL_SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Station> findAll() {
        return jdbcTemplate.findAll(SQL_SELECT_ALL);
    }

    @Override
    public Station insert(Station station) {
        Objects.requireNonNull(station);

        long generatedId = jdbcTemplate.executeInsertWithGeneratedPrimaryKey(
                SQL_INSERT,
                station.getName(),
                station.getCity(),
                station.getCountry()
        );

        station.setId(generatedId);

        return station;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.executeUpdate(SQL_DELETE + WHERE_ID, id);
    }

    @Override
    public void update(Station station) {
        Objects.requireNonNull(station);

        jdbcTemplate.executeUpdate(
                SQL_UPDATE + WHERE_ID,
                station.getName(),
                station.getCity(),
                station.getCountry(),
                station.getId()
        );
    }
}
