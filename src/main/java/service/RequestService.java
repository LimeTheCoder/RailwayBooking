package service;


import entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    Optional<Request> findById(Long id);
    List<Request> findByPassengerId(Long id);
    Request createRequest(Request request);
}
