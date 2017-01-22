package service;


import entity.Route;

import java.util.List;
import java.util.Optional;

public interface RouteService {
    Optional<Route> findById(Long id);
    List<Route> findAll();
}
