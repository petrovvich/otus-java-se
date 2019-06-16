package ru.petrovich.study.frontend;

import lombok.extern.slf4j.Slf4j;
import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.Message;
import ru.petrovich.study.messaging.MessageSystem;
import ru.petrovich.study.messaging.MessageSystemContext;
import ru.petrovich.study.messaging.custom.MsgGetCount;
import ru.petrovich.study.messaging.custom.MsgGetUserById;
import ru.petrovich.study.messaging.custom.MsgGetUserByName;
import ru.petrovich.study.messaging.custom.MsgSaveNewUser;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageSystemContext context;

    private final Map<String, UserDataSet> users = new HashMap<>();
    private long countUsers = 0;

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void handleRequest() {
        Message message = new MsgGetCount(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequest(String login) {
        Message message = new MsgGetUserByName(getAddress(), context.getDbAddress(), login);
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequest(Long id) {
        Message message = new MsgGetUserById(getAddress(), context.getDbAddress(), id);
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequest(String login, String password, String phone, String city, String street, String house) {
        Message message = new MsgSaveNewUser(getAddress(), context.getDbAddress(), login, password, phone, city, street, house);
        context.getMessageSystem().sendMessage(message);
    }

    public void addUser(String name, UserDataSet userDataSet) {
        users.put(name, userDataSet);
        log.info("Return from backend for name {} user {}", name, userDataSet);
    }

    @Override
    public Map<String, UserDataSet> getUsers() {
        return users;
    }


    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    public long getCountUsers() {
        return countUsers;
    }

    public void setCountUsers(long countUsers) {
        this.countUsers = countUsers;
    }
}
