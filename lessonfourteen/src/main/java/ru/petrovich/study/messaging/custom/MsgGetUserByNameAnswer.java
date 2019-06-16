package ru.petrovich.study.messaging.custom;

import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.frontend.FrontendService;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MsgToFrontend;

public class MsgGetUserByNameAnswer extends MsgToFrontend {

    private final String login;
    private final UserDataSet user;

    public MsgGetUserByNameAnswer(Address from, Address to, String login, UserDataSet user) {
        super(from, to);
        this.login = login;
        this.user = user;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(login, user);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MsgGetUserByNameAnswer{");
        sb.append("login='").append(login).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
