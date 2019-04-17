package ru.petrovvich.study.service;

import ru.petrovvich.study.model.UserDataSet;

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
