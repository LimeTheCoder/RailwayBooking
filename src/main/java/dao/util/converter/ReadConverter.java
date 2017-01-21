package dao.util.converter;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public interface ReadConverter<T> {
    /**
     * Convert data from ResultSet to list of appropriate entities
     *
     * @param resultSet ResultSet, that provides data
     * @return fetched objects
     * @throws SQLException
     */
    List<T> convertToList(ResultSet resultSet) throws SQLException;

    List<T> convertToList(ResultSet resultSet, String prefix) throws SQLException;

    T convertToObject(ResultSet resultSet) throws SQLException;

    T convertToObject(ResultSet resultSet, String prefix) throws SQLException;
}
