package com.limethecoder.service;


import com.limethecoder.entity.Station;

import java.util.List;
import java.util.Optional;

public interface StationService {
    List<Station> findAll();
    Optional<Station> findById(long id);
}
