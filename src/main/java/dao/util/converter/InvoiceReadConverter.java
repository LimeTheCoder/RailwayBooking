package dao.util.converter;


import entity.Invoice;
import entity.Route;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceReadConverter implements ReadConverter<Invoice> {
    private final static String ID_FIELD = "id";

    private final static String ROUTE_PREFIX = "rt_";

    private final ReadConverter<User> userConverter;
    private final ReadConverter<Route> routeConverter;

    public InvoiceReadConverter() {
        this(new UserReadConverter(), new RouteReadConverter());
    }

    public InvoiceReadConverter(ReadConverter<User> userConverter, ReadConverter<Route> routeConverter) {
        this.userConverter = userConverter;
        this.routeConverter = routeConverter;
    }

    @Override
    public List<Invoice> convertToList(ResultSet resultSet) throws SQLException {
        return convertToList(resultSet, "");
    }

    @Override
    public List<Invoice> convertToList(ResultSet resultSet, String prefix) throws SQLException {
        List<Invoice> invoices = new ArrayList<>();

        while (resultSet.next()) {
            invoices.add(convertToObject(resultSet, prefix));
        }

        return invoices;
    }

    @Override
    public Invoice convertToObject(ResultSet resultSet) throws SQLException {
        return convertToObject(resultSet, "");
    }

    @Override
    public Invoice convertToObject(ResultSet resultSet, String prefix) throws SQLException {
        User passenger = userConverter.convertToObject(resultSet);
        Route route = routeConverter.convertToObject(resultSet, ROUTE_PREFIX);
        long id = resultSet.getLong(ID_FIELD);
        return new Invoice(id, passenger, route);
    }
}
