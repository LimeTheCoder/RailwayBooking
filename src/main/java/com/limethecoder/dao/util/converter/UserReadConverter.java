package com.limethecoder.dao.util.converter;


import com.limethecoder.entity.Role;
import com.limethecoder.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserReadConverter implements ReadConverter<User> {
    private final static String EMAIL_FIELD = "email";
    private final static String NAME_FIELD = "name";
    private final static String SURNAME_FIELD = "surname";
    private final static String PASSWORD_FIELD = "password";
    private final static String PHONE_FIELD = "phone";
    private final static String ROLE_FIELD = "role";

    @Override
    public User convertToObject(ResultSet resultSet, String prefix) throws SQLException {
        return User.newBuilder()
                .setEmail(resultSet.getString(prefix + EMAIL_FIELD))
                .setName(resultSet.getString(prefix + NAME_FIELD))
                .setSurname(resultSet.getString(prefix + SURNAME_FIELD))
                .setPassword(resultSet.getString(prefix + PASSWORD_FIELD))
                .setPhone(resultSet.getString(prefix + PHONE_FIELD))
                .setRole(Role.valueOf(resultSet.getString(prefix + ROLE_FIELD)))
                .build();
    }
}
