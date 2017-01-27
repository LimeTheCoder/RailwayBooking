package service.Impl;

import dao.RequestDao;
import dao.connection.DaoConnection;
import dao.factory.DaoFactory;
import entity.Request;
import service.RequestService;

import java.util.List;
import java.util.Optional;


public class RequestServiceImpl implements RequestService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private RequestServiceImpl() {}

    private static class InstanceHolder {
        private final static RequestService INSTANCE = new RequestServiceImpl();
    }

    public static RequestService getInstance() {
        return InstanceHolder.INSTANCE;
    }


    @Override
    public Optional<Request> findById(Long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            RequestDao requestDao = daoFactory.getRequestDao(connection);
            return requestDao.findOne(id);
        }
    }

    @Override
    public List<Request> findAllByPassenger(String email) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            RequestDao requestDao = daoFactory.getRequestDao(connection);
            return requestDao.findAllByPassenger(email);
        }
    }

    @Override
    public Request createRequest(Request request) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            RequestDao requestDao = daoFactory.getRequestDao(connection);
            return requestDao.insert(request);
        }
    }
}
