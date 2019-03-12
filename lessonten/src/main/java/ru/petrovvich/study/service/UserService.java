package ru.petrovvich.study.service;

import ru.petrovvich.study.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {

    void saveUsers(User user) throws SQLException, IllegalAccessException;
    Optional<User> getUser(long id);
}
