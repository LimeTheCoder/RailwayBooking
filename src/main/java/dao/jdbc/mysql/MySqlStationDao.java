package dao.jdbc.mysql;


import dao.StationDao;
import dao.exception.DaoException;
import dao.util.converter.ReadConverter;
import dao.util.converter.StationReadConverter;
import entity.Station;

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

    private final Connection connection;
    private final ReadConverter<Station> converter;

    public MySqlStationDao(Connection connection) {
        this(connection, new StationReadConverter());
    }

    public MySqlStationDao(Connection connection, ReadConverter<Station> converter) {
        this.connection = connection;
        this.converter = converter;
    }

    @Override
    public Optional<Station> findOne(Long id) {
        Objects.requireNonNull(id);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL + WHERE_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Station> stationList = converter.convertToList(resultSet);
                return Optional.ofNullable(stationList.isEmpty() ? null : stationList.get(0));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Station> findAll() {
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
    public Station insert(Station station) {
        Objects.requireNonNull(station);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT)) {

            statement.setString(1, station.getName());
            statement.setString(2, station.getCity());
            statement.setString(3, station.getCountry());

            long id = statement.executeUpdate();
            station.setId(id);

            return station;

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
    public void update(Station station) {
        Objects.requireNonNull(station);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_UPDATE + WHERE_ID)) {

            statement.setString(1, station.getName());
            statement.setString(2, station.getCity());
            statement.setString(3, station.getCountry());
            statement.setLong(4, station.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
