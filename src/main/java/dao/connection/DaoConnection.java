package dao.connection;

/**
 * Class that describes abstract connection to database
 *
 * @author Taras Sakharchuk
 */
public interface DaoConnection extends AutoCloseable {
    /**
     * Begins database transaction.
     */
    void startTransaction();

    /**
     * Commit queries in transaction.
     */
    void commit();

    /**
     * Rollback transaction in case when something goes wrong.
     */
    void rollback();

    /**
     * @return database specific connection
     */
    Object getNativeConnection();

    @Override
    void close();
}