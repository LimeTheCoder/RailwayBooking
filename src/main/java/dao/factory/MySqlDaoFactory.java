package dao.factory;

import dao.*;
import dao.connection.DaoConnection;
import dao.connection.MySqlConnection;
import dao.exception.DaoException;
import dao.jdbc.PooledDataSource;
import dao.jdbc.mysql.MySqlTrainDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class MySqlDaoFactory extends DaoFactory {

    private static final String ERROR_NULLABLE_CONNECTION =
            "Connection cannot be nullable";
    private static final String ERROR_WRONG_CONNECTION_TYPE =
            "Wrong type of native connection. Try another factory for this type";

    private DataSource dataSource = PooledDataSource.getInstance();

    @Override
    public DaoConnection getConnection() {
        try {
            return new MySqlConnection(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public InvoiceDao getInvoiceDao(DaoConnection connection) {
        return null;
    }

    @Override
    public RouteDao getRouteDao(DaoConnection connection) {
        return null;
    }

    @Override
    public StationDao getStationDao(DaoConnection connection) {
        return null;
    }

    @Override
    public TrainDao getTrainDao(DaoConnection connection) {
        return new MySqlTrainDao(getSqlConnection(connection));
    }

    @Override
    public UserDao getUserDao(DaoConnection connection) {
        return null;
    }

    private Connection getSqlConnection(DaoConnection connection) {
        checkConnection(connection);
        return (Connection) connection.getNativeConnection();
    }

    private void checkConnection(DaoConnection connection) {
        if (connection == null || connection.getNativeConnection() == null) {
            throw new DaoException(ERROR_NULLABLE_CONNECTION);
        }
        if (!(connection instanceof MySqlConnection)) {
            throw new DaoException(ERROR_WRONG_CONNECTION_TYPE);
        }
    }
}
