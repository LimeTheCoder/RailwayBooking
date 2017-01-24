package service;


import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    List<User> findAll();
    boolean isCredentialsValid(String email, String password);
    boolean isUserExists(String email);
    User createUser(User user);
}
