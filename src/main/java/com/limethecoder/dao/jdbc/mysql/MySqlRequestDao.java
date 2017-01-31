package com.limethecoder.dao.jdbc.mysql;

import com.limethecoder.dao.RequestDao;
import com.limethecoder.dao.util.Util;
import com.limethecoder.dao.util.converter.ReadConverter;
import com.limethecoder.dao.util.converter.RequestReadConverter;
import com.limethecoder.entity.Request;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlRequestDao extends AbstractDaoTemplate<Request, Long>
        implements RequestDao {

    private final static String SQL_SELECT_ALL =
            "SELECT Requests.departure, Requests.destination, Requests.departure_time, " +
                    "Requests.creation_time, Requests.id, Requests.passenger, Requests.result_cnt, " +
                    "s1.name AS dep_name, s1.city as dep_city, " +
                    "s1.country as dep_country, s1.id as dep_id, " +
                    "s2.name AS dest_name, s2.city as dest_city, " +
                    "s2.country as dest_country, s2.id as dest_id, " +
                    "u.email, u.name, u.surname, u.password, u.phone, u.role " +
                    "FROM Requests " +
                    "JOIN Stations AS s1 ON Requests.departure = s1.id " +
                    "JOIN Stations AS s2 ON Requests.destination = s2.id " +
                    "JOIN Users as u ON Requests.passenger = u.email";

    private final static String SQL_INSERT =
            "INSERT INTO Requests (departure, destination, " +
                    "departure_time, passenger, result_cnt) " +
                    "VALUES (?,?,?,?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Requests";
    private final static String SQL_UPDATE =
            "UPDATE Requests SET departure = ?, destination = ?, " +
                    "departure_time = ?, passenger = ?, result_cnt = ? ";

    private final static String WHERE_ID = " WHERE Requests.id = ? ";
    private final static String WHERE_PASSENGER = " WHERE passenger = ? ";

    private final static String ORDER_BY_ID_DESC = " ORDER BY id DESC";

    public MySqlRequestDao(Connection connection) {
        this(connection, new RequestReadConverter());
    }

    public MySqlRequestDao(Connection connection,
                           ReadConverter<Request> converter) {
        super(connection, converter);
    }

    @Override
    public Optional<Request> findOne(Long id) {
        return findOne(SQL_SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Request> findAll() {
        return findAll(SQL_SELECT_ALL + ORDER_BY_ID_DESC);
    }

    @Override
    public Request insert(Request request) {
        Objects.requireNonNull(request);

        long generatedId = executeInsertWithGeneratedPrimaryKey(
                SQL_INSERT,
                request.getDeparture().getId(),
                request.getDestination().getId(),
                Util.toTimestamp(request.getDepartureTime()),
                request.getPassenger().getEmail(),
                request.getResultCnt()
        );

        request.setId(generatedId);

        return request;
    }

    @Override
    public void delete(Long id) {
        executeUpdate(SQL_DELETE + WHERE_ID, id);
    }

    @Override
    public void update(Request request) {
        Objects.requireNonNull(request);

        executeUpdate(
                SQL_UPDATE + WHERE_ID,
                request.getDeparture().getId(),
                request.getDestination().getId(),
                Util.toTimestamp(request.getDepartureTime()),
                request.getPassenger().getEmail(),
                request.getResultCnt(),
                request.getId()
        );
    }

    @Override
    public List<Request> findAllByPassenger(String email) {
        Objects.requireNonNull(email);

        return findAll(SQL_SELECT_ALL + WHERE_PASSENGER + ORDER_BY_ID_DESC, email);
    }
}
