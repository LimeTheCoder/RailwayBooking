package dao.jdbc.mysql;

import dao.InvoiceDao;
import dao.exception.DaoException;
import dao.util.converter.InvoiceReadConverter;
import dao.util.converter.ReadConverter;
import entity.Invoice;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MySqlInvoiceDao implements InvoiceDao {
    private final static String SQL_SELECT_ALL =
            "SELECT i.id, u.email, u.name, u.surname, u.password, u.phone, u.role, " +
                    "r.departure_station AS rt_departure_station, " +
                    "r.destination_station AS rt_destination_station, " +
                    "r.departure_time AS rt_departure_time, " +
                    "r.destination_time AS rt_destination_time, " +
                    "r.price AS rt_price, r.train as rt_train, r.id as rt_id, " +
                    "s1.name AS rt_dep_name, s1.city as rt_dep_city, " +
                    "s1.country as rt_dep_country, " +
                    "s1.id as rt_dep_id, s2.name AS rt_dest_name, " +
                    "s2.city as rt_dest_city, " +
                    "s2.country as rt_dest_country, s2.id as rt_dest_id, " +
                    "t1.serial_no as rt_tr_serial_no, t1.capacity as rt_tr_capacity " +
                    "FROM Invoices AS i " +
                    "JOIN Routes as r on r.id = i.route " +
                    "JOIN Users as u on u.email = i.passenger " +
                    "JOIN Stations AS s1 ON r.departure_station = s1.id " +
                    "JOIN Stations AS s2 ON r.destination_station = s2.id " +
                    "JOIN Trains as t1 ON r.train = t1.serial_no";
    private final static String SQL_INSERT =
            "INSERT INTO Invoices (passenger, route) VALUES (?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Invoices";
    private final static String SQL_UPDATE =
            "UPDATE Invoices SET passenger = ?, route = ? ";

    private final static String WHERE_ID = " WHERE id = ?";

    private final Connection connection;
    private final ReadConverter<Invoice> converter;

    public MySqlInvoiceDao(Connection connection) {
        this(connection, new InvoiceReadConverter());
    }

    public MySqlInvoiceDao(Connection connection,
                           ReadConverter<Invoice> converter) {
        this.connection = connection;
        this.converter = converter;
    }

    @Override
    public Optional<Invoice> findOne(Long id) {
        Objects.requireNonNull(id);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL + WHERE_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Invoice> invoices = converter.convertToList(resultSet);
                return Optional.ofNullable(invoices.isEmpty() ? null : invoices.get(0));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Invoice> findAll() {
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
    public Invoice insert(Invoice invoice) {
        Objects.requireNonNull(invoice);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT)) {

            statement.setString(1, invoice.getPassenger().getEmail());
            statement.setLong(2, invoice.getRoute().getId());

            long id = statement.executeUpdate();
            invoice.setId(id);

            return invoice;

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
    public void update(Invoice invoice) {
        Objects.requireNonNull(invoice);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT + WHERE_ID)) {

            statement.setString(1, invoice.getPassenger().getEmail());
            statement.setLong(2, invoice.getRoute().getId());
            statement.setLong(3, invoice.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
