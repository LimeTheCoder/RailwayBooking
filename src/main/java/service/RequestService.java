package service;


import entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    Optional<Request> findById(Long id);
    List<Request> findAllByPassenger(String email);
    Request createRequest(Request request);
}
