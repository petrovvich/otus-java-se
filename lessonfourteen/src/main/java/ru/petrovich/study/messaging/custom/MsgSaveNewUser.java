package ru.petrovich.study.messaging.custom;

import ru.petrovich.study.backend.BackendService;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MsgToDB;

public class MsgSaveNewUser extends MsgToDB {

    private final String login;
    private final String password;
    private final String phone;
    private final String city;
    private final String street;
    private final String house;

    public MsgSaveNewUser(Address from, Address to, String login, String password, String phone, String city, String street, String house) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    @Override
    public void exec(BackendService backendService) {
        backendService.getUserByName(login);
    }
}
