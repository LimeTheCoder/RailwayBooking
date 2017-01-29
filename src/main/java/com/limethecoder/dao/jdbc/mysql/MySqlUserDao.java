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


public class MySqlUserDao implements UserDao {
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

    private final Connection connection;
    private final ReadConverter<User> converter;

    public MySqlUserDao(Connection connection) {
        this(connection, new UserReadConverter());
    }

    public MySqlUserDao(Connection connection, ReadConverter<User> converter) {
        this.connection = connection;
        this.converter = converter;
    }

    @Override
    public Optional<User> findOne(String email) {
        Objects.requireNonNull(email);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_SELECT_ALL + WHERE_EMAIL)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<User> users = converter.convertToList(resultSet);
                return Optional.ofNullable(users.isEmpty() ? null : users.get(0));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {
            return converter.convertToList(resultSet);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isExist(String email) {
        return findOne(email).isPresent();
    }

    @Override
    public User insert(User user) {
        Objects.requireNonNull(user);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getRole().name());
            statement.executeUpdate();

            return user;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(String email) {
        Objects.requireNonNull(email);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_DELETE + WHERE_EMAIL)) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user) {
        Objects.requireNonNull(user);

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_UPDATE + WHERE_EMAIL)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole().name());
            statement.setString(6, user.getEmail());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
