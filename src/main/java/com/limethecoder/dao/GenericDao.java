package com.limethecoder.dao;

import java.util.List;
import java.util.Optional;

/**
 * Common interface for all dao in system.
 *
 * @param <T> type of domain object
 * @param <ID> type of identifier
 */
public interface GenericDao<T, ID> {
    /**
     * Retrieves one object from database based on id of object.
     *
     * @param id object identifier
     * @return optional, which contains founded object or null
     */
    Optional<T> findOne(ID id);

    /**
     * Retrieves all object from database.
     *
     * @return list of retrieved objects
     */
    List<T> findAll();

    /**
     * Insert new entity to database.
     *
     * @param obj for insertion
     * @return inserted object
     */
    T insert(T obj);

    /**
     * Delete entity from database based on id argument.
     *
     * @param id to specify object to delete
     */
    void delete(ID id);

    /**
     * Update object in database.
     *
     * @param obj with updated fields
     */
    void update(T obj);

    /**
     * Performs checking for existing object in database.
     *
     * @param id specify object
     * @return {@code true} if exists else {@code false}
     */
    default boolean isExist(ID id) {
        return findOne(id).isPresent();
    }
}