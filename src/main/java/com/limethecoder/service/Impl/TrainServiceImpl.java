package com.limethecoder.service.Impl;

import com.limethecoder.dao.TrainDao;
import com.limethecoder.dao.connection.DaoConnection;
import com.limethecoder.dao.factory.DaoFactory;
import com.limethecoder.entity.Train;
import com.limethecoder.service.TrainService;

import java.util.List;


public class TrainServiceImpl implements TrainService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    TrainServiceImpl() {}

    private static class InstanceHolder {
        private final static TrainService INSTANCE = new TrainServiceImpl();
    }

    public static TrainService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public List<Train> findAll() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            TrainDao trainDao = daoFactory.getTrainDao(connection);
            return trainDao.findAll();
        }
    }
}
