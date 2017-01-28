package service.Impl;

import dao.RouteDao;
import dao.connection.DaoConnection;
import dao.factory.DaoFactory;
import entity.Route;
import entity.Station;
import service.RouteService;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public class RouteServiceImpl implements RouteService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private RouteServiceImpl() {}

    private static class InstanceHolder {
        private final static RouteService INSTANCE = new RouteServiceImpl();
    }

    public static RouteService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<Route> findById(Long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            RouteDao routeDao = daoFactory.getRouteDao(connection);
            return routeDao.findOne(id);
        }
    }

    @Override
    public List<Route> findAll() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            RouteDao routeDao = daoFactory.getRouteDao(connection);
            return routeDao.findAll();
        }
    }

    @Override
    public List<Route> findByStations(Station from, Station to) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            RouteDao routeDao = daoFactory.getRouteDao(connection);
            return routeDao.findByStations(from, to);
        }
    }

    @Override
    public List<Route> findByStationsAndDate(Station from, Station to, Date after) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            RouteDao routeDao = daoFactory.getRouteDao(connection);
            return routeDao.findByStationsAndDate(from, to, after);
        }
    }
}
