package com.limethecoder.service.Impl;


import com.limethecoder.dao.UserDao;
import com.limethecoder.dao.connection.DaoConnection;
import com.limethecoder.dao.factory.DaoFactory;
import com.limethecoder.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import com.limethecoder.service.UserService;

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
                    .filter(u -> BCrypt.checkpw(password, u.getPassword()))
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

        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hash);

        try(DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.insert(user);
        }
    }
}
