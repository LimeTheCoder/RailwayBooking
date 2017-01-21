package dao.connection;

public interface DaoConnection extends AutoCloseable {
    void startTransaction();

    void commit();

    void rollback();

    void close();

    Object getNativeConnection();
}