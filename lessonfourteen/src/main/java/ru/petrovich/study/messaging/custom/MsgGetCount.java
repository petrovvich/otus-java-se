package ru.petrovich.study.messaging.custom;

import ru.petrovich.study.backend.BackendService;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MsgToDB;

public class MsgGetCount extends MsgToDB {

    public MsgGetCount(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(BackendService backendService) {
        long count = backendService.getCountUsers();
        backendService.getMS().sendMessage(new MsgGetCountAnswer(getTo(), getFrom(), count));
    }
}
