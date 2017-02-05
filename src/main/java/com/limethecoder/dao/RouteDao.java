package com.limethecoder.dao;


import com.limethecoder.entity.Route;
import com.limethecoder.entity.Station;

import java.util.Date;
import java.util.List;

public interface RouteDao extends GenericDao<Route, Long> {
    List<Route> findByStations(Station from, Station to);
    List<Route> findByStationsAndDate(Station from,
                                      Station to, Date after);
    void incrementReservedCnt(long id);
    void decrementReservedCnt(long id);

    int findReservedCnt(long id);
}
