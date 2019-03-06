package ru.petrovvich.study.service;

import ru.petrovvich.study.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {

    long saveUsers(User user) throws SQLException;
    Optional<User> getUser(long id);
}
