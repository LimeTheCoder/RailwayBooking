package com.limethecoder.dao.jdbc.mysql.converter;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converter interface from resultSet to appropriate com.limethecoder.entity object
 * @param <T> type of com.limethecoder.entity
 *
 * @author Taras Sakharchuk
 */
public interface ReadConverter<T> {

    /**
     * Convert data from resultSet to list of domain objects.
     *
     * @param resultSet
     * @param prefix
     * @return
     * @throws SQLException
     */
    default List<T> convertToList(ResultSet resultSet, String prefix)
            throws SQLException {
        List<T> objects = new ArrayList<>();

        while (resultSet.next()) {
            objects.add(convertToObject(resultSet, prefix));
        }

        return objects;
    }

    /**
     * Wrapper function for case if columns without prefixes
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    default List<T> convertToList(ResultSet resultSet) throws SQLException {
        return convertToList(resultSet, "");
    }

    default T convertToObject(ResultSet resultSet) throws SQLException {
        return convertToObject(resultSet, "");
    }

    /**
     * Converts data from resultSet to domain com.limethecoder.entity
     *
     * @param resultSet
     * @param prefix
     * @return com.limethecoder.entity, populated with data from resultSet
     * @throws SQLException
     */
    T convertToObject(ResultSet resultSet, String prefix) throws SQLException;
}