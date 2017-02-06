package com.limethecoder.dao.connection;

/**
 * Class that describes abstract connection to database
 *
 * @author Taras Sakharchuk
 */
public interface DaoConnection extends AutoCloseable {
    /** Begins database transaction. */
    void startTransaction();

    /** Begins database transaction with serializable isolation level. */
    void startSerializableTransaction();

    /** Commit transaction. */
    void commit();

    /** Rollback transaction in case when something goes wrong. */
    void rollback();

    /**
     * @return database specific connection
     */
    Object getNativeConnection();

    @Override
    void close();
}