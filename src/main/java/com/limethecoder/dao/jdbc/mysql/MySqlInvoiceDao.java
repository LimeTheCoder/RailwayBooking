package com.limethecoder.dao.jdbc.mysql;

import com.limethecoder.dao.InvoiceDao;
import com.limethecoder.dao.jdbc.mysql.converter.InvoiceReadConverter;
import com.limethecoder.dao.jdbc.mysql.converter.ReadConverter;
import com.limethecoder.entity.Invoice;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MySqlInvoiceDao implements InvoiceDao {

    private final static String SQL_SELECT_ALL =
            "SELECT Invoices.id, Invoices.status, " +
                    "r.departure_station AS rt_departure_station, " +
                    "r.destination_station AS rt_destination_station, " +
                    "r.departure_time AS rt_departure_time, " +
                    "r.destination_time AS rt_destination_time, " +
                    "r.reserved_cnt as rt_reserved_cnt, " +
                    "r.price AS rt_price, r.train as rt_train, r.id as rt_id, " +
                    "s1.name AS rt_dep_name, s1.city as rt_dep_city, " +
                    "s1.country as rt_dep_country, " +
                    "s1.id as rt_dep_id, s2.name AS rt_dest_name, " +
                    "s2.city as rt_dest_city, " +
                    "s2.country as rt_dest_country, s2.id as rt_dest_id, " +
                    "t1.serial_no as rt_tr_serial_no, t1.capacity as rt_tr_capacity, " +
                    "u.email as req_email, u.name as req_name, u.surname as req_surname, " +
                    "u.password as req_password, u.phone as req_phone, u.role as req_role, " +
                    "req.departure_time as req_departure_time, req.result_cnt as req_result_cnt, " +
                    "req.creation_time as req_creation_time, req.id as req_id, " +
                    "s3.name AS req_dep_name, s3.city as req_dep_city, " +
                    "s3.country as req_dep_country, " +
                    "s3.id as req_dep_id, s4.name AS req_dest_name, " +
                    "s4.city as req_dest_city, " +
                    "s4.country as req_dest_country, s4.id as req_dest_id " +
                    "FROM Invoices " +
                    "JOIN Routes as r on r.id = Invoices.route " +
                    "JOIN Stations AS s1 ON r.departure_station = s1.id " +
                    "JOIN Stations AS s2 ON r.destination_station = s2.id " +
                    "JOIN Trains as t1 ON r.train = t1.serial_no " +
                    "JOIN Requests as req on Invoices.request = req.id " +
                    "JOIN Users as u on u.email = req.passenger " +
                    "JOIN Stations AS s3 ON req.departure = s3.id " +
                    "JOIN Stations AS s4 ON req.destination = s4.id ";
    private final static String SQL_INSERT =
            "INSERT INTO Invoices (request, route, status) VALUES (?,?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Invoices";
    private final static String SQL_UPDATE =
            "UPDATE Invoices SET request = ?, route = ?, status = ? ";
    private final static String SQL_UPDATE_STATUS =
            "UPDATE Invoices SET status = ? ";

    private final static String WHERE_ID = " WHERE Invoices.id = ?";
    private final static String WHERE_PASSENGER_ID =
            " WHERE (SELECT passenger FROM Requests WHERE Requests.id = request) = ?";
    private final static String WHERE_ROUTE_ID = " WHERE route = ?";
    private final static String WHERE_STATUS = " WHERE status = ?";

    private final JdbcDaoTemplate<Invoice> jdbcTemplate;

    public MySqlInvoiceDao(Connection connection) {
        this(connection, new InvoiceReadConverter());
    }

    public MySqlInvoiceDao(Connection connection,
                           ReadConverter<Invoice> converter) {
        jdbcTemplate = new JdbcDaoTemplate<>(connection, converter);
    }

    public MySqlInvoiceDao(JdbcDaoTemplate<Invoice> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Invoice> findOne(Long id) {
        return jdbcTemplate.findOne(SQL_SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Invoice> findAll() {
        return jdbcTemplate.findAll(SQL_SELECT_ALL);
    }

    @Override
    public List<Invoice> findAllByPassenger(String passengerEmail) {
        return jdbcTemplate.findAll(
                SQL_SELECT_ALL + WHERE_PASSENGER_ID,
                passengerEmail
        );
    }

    @Override
    public List<Invoice> findAllByRoute(Long routeId) {
        return jdbcTemplate.findAll(SQL_SELECT_ALL + WHERE_ROUTE_ID, routeId);
    }

    @Override
    public List<Invoice> findAllByStatus(Invoice.Status status) {
        return jdbcTemplate.findAll(SQL_SELECT_ALL + WHERE_STATUS, status.name());
    }

    @Override
    public void updateInvoiceStatus(Long id, Invoice.Status status) {
        jdbcTemplate.executeUpdate(
                SQL_UPDATE_STATUS + WHERE_ID,
                status.name(),
                id
        );
    }

    @Override
    public Invoice insert(Invoice invoice) {
        Objects.requireNonNull(invoice);

        long generatedId = jdbcTemplate.executeInsertWithGeneratedPrimaryKey(
                SQL_INSERT,
                invoice.getRequest().getId(),
                invoice.getRoute().getId(),
                invoice.getStatus().name()
        );

        invoice.setId(generatedId);

        return invoice;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.executeUpdate(SQL_DELETE + WHERE_ID, id);
    }

    @Override
    public void update(Invoice invoice) {
        Objects.requireNonNull(invoice);

        jdbcTemplate.executeUpdate(
                SQL_UPDATE + WHERE_ID,
                invoice.getRequest().getId(),
                invoice.getRoute().getId(),
                invoice.getStatus().name(),
                invoice.getId()
        );
    }
}
