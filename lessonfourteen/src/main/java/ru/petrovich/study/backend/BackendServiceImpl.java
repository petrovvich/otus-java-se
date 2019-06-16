package ru.petrovich.study.backend;

import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.backend.service.UserService;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MessageSystem;
import ru.petrovich.study.messaging.MessageSystemContext;

public class BackendServiceImpl implements BackendService {
    private final Address address;
    private final MessageSystemContext context;
    private final UserService userService;

    public BackendServiceImpl(MessageSystemContext context, Address address, UserService userService) {
        this.context = context;
        this.address = address;
        this.userService = userService;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    public int getUserId(String name) {
        return 1;
    }

    @Override
    public UserDataSet getUserByName(String name) {
        return userService.findByName(name);
    }

    @Override
    public UserDataSet getUserById(long id) {
        return userService.findById(id);
    }

    @Override
    public void saveNew(String login, String password, String phone, String city, String street, String house) {
        userService.saveNew(login, password, phone, city, street, house);
    }

    @Override
    public long getCountUsers() {
        return userService.getCountUsers();
    }
}