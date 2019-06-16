package ru.petrovich.study.backend.service;

import ru.petrovich.study.backend.model.UserDataSet;

import java.util.List;

public interface DBService {
    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();

    UserDataSet readByNameAndPassword(String name, String password);
}
