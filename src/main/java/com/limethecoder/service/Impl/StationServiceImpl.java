package com.limethecoder.service.Impl;

import com.limethecoder.dao.StationDao;
import com.limethecoder.dao.connection.DaoConnection;
import com.limethecoder.dao.factory.DaoFactory;
import com.limethecoder.entity.Station;
import com.limethecoder.service.StationService;

import java.util.List;
import java.util.Optional;


public class StationServiceImpl implements StationService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private StationServiceImpl() {}

    private static class InstanceHolder {
        private final static StationService INSTANCE = new StationServiceImpl();
    }

    public static StationService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public List<Station> findAll() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            StationDao stationDao = daoFactory.getStationDao(connection);
            return stationDao.findAll();
        }
    }

    @Override
    public Optional<Station> findById(long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            StationDao stationDao = daoFactory.getStationDao(connection);
            return stationDao.findOne(id);
        }
    }
}
