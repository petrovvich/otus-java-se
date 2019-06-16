package ru.petrovich.study.messaging.custom;

import ru.petrovich.study.backend.BackendService;
import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MsgToDB;

public class MsgGetUserById extends MsgToDB {
    private final long id;

    public MsgGetUserById(Address from, Address to, long id) {
        super(from, to);
        this.id = id;
    }

    @Override
    public void exec(BackendService backendService) {
        UserDataSet userDataSet = backendService.getUserById(id);
        backendService.getMS().sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), id, userDataSet));
    }
}
