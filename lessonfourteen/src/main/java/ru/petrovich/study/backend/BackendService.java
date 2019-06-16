package ru.petrovich.study.backend;

import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.messaging.Addressee;

public interface BackendService extends Addressee {
    void init();

    int getUserId(String name);

    UserDataSet getUserByName(String name);

    UserDataSet getUserById(long id);

    void saveNew(String login, String password, String phone, String city, String street, String house);

    long getCountUsers();
}

