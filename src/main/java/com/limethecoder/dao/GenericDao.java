package com.limethecoder.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {
    Optional<T> findOne(ID id);

    List<T> findAll();

    boolean isExist(ID id);

    T insert(T obj);

    void delete(ID id);

    void update(T obj);
}