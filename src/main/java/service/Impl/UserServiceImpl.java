package service.Impl;


import dao.UserDao;
import dao.connection.DaoConnection;
import dao.factory.DaoFactory;
import entity.User;
import service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private UserServiceImpl() {}

    private static class InstanceHolder {
        private final static UserService INSTANCE = new UserServiceImpl();
    }

    public static UserService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findOne(email);
        }
    }

    @Override
    public List<User> findAll() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findAll();
        }
    }
}
