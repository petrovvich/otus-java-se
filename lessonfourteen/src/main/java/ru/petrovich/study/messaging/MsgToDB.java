package ru.petrovich.study.messaging;

import ru.petrovich.study.backend.BackendService;

public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof BackendService) {
            exec((BackendService) addressee);
        }
    }

    public abstract void exec(BackendService backendService);
}
