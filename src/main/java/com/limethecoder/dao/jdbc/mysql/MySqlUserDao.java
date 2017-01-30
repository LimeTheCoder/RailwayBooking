package com.limethecoder.dao.jdbc.mysql;

import com.limethecoder.dao.UserDao;
import com.limethecoder.dao.exception.DaoException;
import com.limethecoder.dao.util.converter.ReadConverter;
import com.limethecoder.dao.util.converter.UserReadConverter;
import com.limethecoder.entity.User;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MySqlUserDao extends AbstractDao<User, String>
        implements UserDao {

    private final static String SQL_SELECT_ALL =
            " SELECT * FROM Users ";
    private final static String SQL_INSERT =
            "INSERT INTO Users (email, name, surname, password, phone, role)" +
                    " VALUES (?,?,?,?,?,?) ";
    private final static String SQL_DELETE = "DELETE FROM Users";
    private final static String SQL_UPDATE =
            "UPDATE Users SET name = ?, surname = ?, password = ?, " +
                    "phone = ?, role = ?  ";

    private final static String WHERE_EMAIL = " WHERE email = ?";

    public MySqlUserDao(Connection connection) {
        this(connection, new UserReadConverter());
    }

    public MySqlUserDao(Connection connection,
                        ReadConverter<User> converter) {
        super(connection, converter);
    }

    @Override
    public Optional<User> findOne(String email) {
        Objects.requireNonNull(email);

        return findOne(SQL_SELECT_ALL + WHERE_EMAIL, email);
    }

    @Override
    public List<User> findAll() {
        return findAll(SQL_SELECT_ALL);
    }

    @Override
    public User insert(User user) {
        Objects.requireNonNull(user);

        executeUpdate(
                SQL_INSERT,
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getPhone(),
                user.getRole().name()
        );

        return user;
    }

    @Override
    public void delete(String email) {
        Objects.requireNonNull(email);

        executeUpdate(SQL_DELETE + WHERE_EMAIL, email);
    }

    @Override
    public void update(User user) {
        Objects.requireNonNull(user);

        executeUpdate(
                SQL_UPDATE + WHERE_EMAIL,
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getPhone(),
                user.getRole().name(),
                user.getEmail()
        );
    }
}
