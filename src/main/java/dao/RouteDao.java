package dao;


import entity.Route;
import entity.Station;

import java.util.List;

public interface RouteDao extends GenericDao<Route, Long> {
    List<Route> findByStations(Station from, Station to);
}
