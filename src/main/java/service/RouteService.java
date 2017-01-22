package service;


import entity.Route;
import entity.Station;

import java.util.List;
import java.util.Optional;

public interface RouteService {
    Optional<Route> findById(Long id);
    List<Route> findAll();
    List<Route> findByStations(Station from, Station to);
}
