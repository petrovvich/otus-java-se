package ru.petrovich.study.frontend;

import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.messaging.Addressee;

import java.util.Map;

public interface FrontendService extends Addressee {
    void init();

    void handleRequest();

    void handleRequest(String login);

    void handleRequest(Long id);

    void handleRequest(String login, String password, String phone, String city, String street, String house);

    void addUser(String name, UserDataSet userDataSet);

    Map<String, UserDataSet> getUsers();

    long getCountUsers();

    void setCountUsers(long countUsers);
}
