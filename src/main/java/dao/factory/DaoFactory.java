package dao.factory;

import dao.*;
import dao.connection.DaoConnection;
import dao.exception.DaoException;

import java.util.ResourceBundle;

/**
 * Factory, that reproduce DAO entities.
 *
 * @author Taras Sakharchuk
 *
 * @see GenericDao
 */
public abstract class DaoFactory {
    private static final String DB_BUNDLE = "db";
    private static final String DB_FACTORY_CLASS = "factory.class";
    private static DaoFactory instance;

    public static DaoFactory getInstance(){
        if(instance == null) {
            ResourceBundle properties = ResourceBundle.getBundle(DB_BUNDLE);
            String factoryClass = properties.getString(DB_FACTORY_CLASS);
            try {
                instance = (DaoFactory) Class.forName(factoryClass).newInstance();
            } catch (Exception e) {
                throw new DaoException(e);
            }
        }

        return instance;
    }

    public abstract DaoConnection getConnection();

    public abstract InvoiceDao getInvoiceDao(DaoConnection connection);
    public abstract RouteDao getRouteDao(DaoConnection connection);
    public abstract StationDao getStationDao(DaoConnection connection);
    public abstract TrainDao getTrainDao(DaoConnection connection);
    public abstract UserDao getUserDao(DaoConnection connection);
}