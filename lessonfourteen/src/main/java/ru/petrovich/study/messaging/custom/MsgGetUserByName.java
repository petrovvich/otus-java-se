package ru.petrovich.study.messaging.custom;

import ru.petrovich.study.backend.BackendService;
import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MsgToDB;

public class MsgGetUserByName extends MsgToDB {
    private final String login;

    public MsgGetUserByName(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    @Override
    public void exec(BackendService backendService) {
        UserDataSet userDataSet = backendService.getUserByName(login);
        backendService.getMS().sendMessage(new MsgGetUserByNameAnswer(getTo(), getFrom(), login, userDataSet));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MsgGetUserByName{");
        sb.append("login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
