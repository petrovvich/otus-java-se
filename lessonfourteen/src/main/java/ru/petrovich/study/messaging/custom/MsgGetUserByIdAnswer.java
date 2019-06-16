package ru.petrovich.study.messaging.custom;

import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.frontend.FrontendService;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MsgToFrontend;

public class MsgGetUserByIdAnswer extends MsgToFrontend {

    private final long id;
    private final UserDataSet user;

    public MsgGetUserByIdAnswer(Address from, Address to, long id, UserDataSet user) {
        super(from, to);
        this.id = id;
        this.user = user;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(String.valueOf(id), user);
    }
}
