package service;


import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
