package dao.util.converter;


import entity.Role;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserReadConverter implements ReadConverter<User> {
    private final static String EMAIL_FIELD = "email";
    private final static String NAME_FIELD = "name";
    private final static String SURNAME_FIELD = "surname";
    private final static String PASSWORD_FIELD = "password";
    private final static String PHONE_FIELD = "phone";
    private final static String ROLE_FIELD = "role";

    @Override
    public List<User> convertToList(ResultSet resultSet) throws SQLException {
        return convertToList(resultSet, "");
    }

    @Override
    public List<User> convertToList(ResultSet resultSet, String prefix) throws SQLException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            users.add(convertToObject(resultSet, prefix));
        }

        return users;
    }

    @Override
    public User convertToObject(ResultSet resultSet) throws SQLException {
        return convertToObject(resultSet, "");
    }

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
