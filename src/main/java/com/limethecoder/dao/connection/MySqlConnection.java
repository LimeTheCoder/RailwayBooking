package com.limethecoder.dao.connection;

import com.limethecoder.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;


public class MySqlConnection implements DaoConnection {
    private static final String ERROR_DURING_START =
            "Failed to start transaction";
    private static final String ERROR_DURING_COMMIT =
            "Failed to commit transaction";
    private static final String ERROR_DURING_ROLLBACK =
            "Failed to rollback transaction";
    private static final String ERROR_DURING_CLOSE =
            "Failed to close transaction";

    /** Concrete base-related connection*/
    private Connection connection;

    private boolean isTransactionStarted;

    private boolean isTransactionCommitted;

    public MySqlConnection(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
            isTransactionStarted = true;
        } catch (SQLException e) {
            throw new DaoException(ERROR_DURING_START, e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            isTransactionCommitted = true;
        } catch (SQLException e) {
            throw new DaoException(ERROR_DURING_COMMIT, e);
        }
    }

    @Override
    public void rollback() {
        try {
            isTransactionCommitted = true;
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(ERROR_DURING_ROLLBACK, e);
        }
    }

    @Override
    public void close() {
        try {
            if (isTransactionStarted && !isTransactionCommitted) {
                rollback();
            }
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(ERROR_DURING_CLOSE, e);
        }
    }

    @Override
    public Object getNativeConnection() {
        return connection;
    }
}
