package ru.petrovich.study.backend.service;

import ru.petrovich.study.backend.model.UserDataSet;

public interface UserService {

    UserDataSet findByNameAndPassword(String name, String password);

    UserDataSet findByName(String name);

    UserDataSet findById(Long id);

    Integer getCountUsers();

    boolean saveNew(String name, String password, String phone, String city, String street, String house);

    boolean exist(String login);
}
