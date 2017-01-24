package service.Impl;


import dao.UserDao;
import dao.connection.DaoConnection;
import dao.factory.DaoFactory;
import entity.User;
import service.UserService;

import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean isCredentialsValid(String email, String password) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            Optional<User> user = userDao.findOne(email);
            return user
                    .filter(u -> u.getPassword().equals(password))
                    .isPresent();
        }
    }

    @Override
    public boolean isUserExists(String email) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.isExist(email);
        }
    }

    @Override
    public User createUser(User user) {
        Objects.requireNonNull(user);

        if(user.getRole() == null) {
            user.setDefaultRole();
        }

        try(DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.insert(user);
        }
    }
}
