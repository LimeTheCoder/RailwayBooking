package com.limethecoder.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {
    Optional<T> findOne(ID id);

    List<T> findAll();

    T insert(T obj);

    void delete(ID id);

    void update(T obj);

    default boolean isExist(ID id) {
        return findOne(id).isPresent();
    }
}