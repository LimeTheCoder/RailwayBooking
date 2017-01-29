package com.limethecoder.dao;

import com.limethecoder.entity.Request;

import java.util.List;


public interface RequestDao extends GenericDao<Request, Long> {
    List<Request> findAllByPassenger(String email);
}
