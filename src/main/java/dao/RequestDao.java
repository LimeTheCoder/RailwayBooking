package dao;

import entity.Request;

import java.util.List;


public interface RequestDao extends GenericDao<Request, Long> {
    List<Request> findAllByPassenger(String email);
}
