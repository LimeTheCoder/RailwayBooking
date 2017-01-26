package dao.util.converter;


import entity.Invoice;
import entity.Request;
import entity.Route;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceReadConverter implements ReadConverter<Invoice> {
    private final static String ID_FIELD = "id";
    private final static String STATUS_FIELD = "status";

    private final static String ROUTE_PREFIX = "rt_";
    private final static String REQUEST_PREFIX = "req_";

    private final ReadConverter<Request> requestConverter;
    private final ReadConverter<Route> routeConverter;

    public InvoiceReadConverter() {
        this(new RequestReadConverter(), new RouteReadConverter());
    }

    public InvoiceReadConverter(ReadConverter<Request> requestConverter, ReadConverter<Route> routeConverter) {
        this.requestConverter = requestConverter;
        this.routeConverter = routeConverter;
    }

    @Override
    public Invoice convertToObject(ResultSet resultSet, String prefix) throws SQLException {
        Request request = requestConverter.convertToObject(resultSet, prefix + REQUEST_PREFIX);
        Route route = routeConverter.convertToObject(resultSet, prefix + ROUTE_PREFIX);
        return Invoice.newBuilder()
                .setId(resultSet.getLong(prefix + ID_FIELD))
                .setStatus(Invoice.Status.valueOf(resultSet
                        .getString(prefix + STATUS_FIELD)))
                .setRequest(request)
                .setRoute(route)
                .build();
    }
}
