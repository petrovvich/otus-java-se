package ru.petrovich.study.messaging.custom;

import ru.petrovich.study.frontend.FrontendService;
import ru.petrovich.study.messaging.Address;
import ru.petrovich.study.messaging.MsgToFrontend;

public class MsgGetCountAnswer extends MsgToFrontend {

    private long count;

    public MsgGetCountAnswer(Address from, Address to, long count) {
        super(from, to);
        this.count = count;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.setCountUsers(count);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MsgGetCountAnswer{");
        sb.append("count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
