package ru.petrovich.study.messaging;

import lombok.extern.slf4j.Slf4j;
import ru.petrovich.study.frontend.FrontendService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Slf4j
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            log.error("Addressee not implemented {}", addressee);
            throw new NotImplementedException();
        }
    }

    public abstract void exec(FrontendService frontendService);
}
