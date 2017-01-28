package service;


import entity.Station;

import java.util.List;
import java.util.Optional;

public interface StationService {
    List<Station> findAll();
    Optional<Station> findById(long id);
}
