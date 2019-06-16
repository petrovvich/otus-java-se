package ru.petrovich.study.messaging;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("LoopStatementThatDoesntLoop")
@Slf4j
public final class MessageSystem {
    private final List<Thread> workers;
    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
    private final Map<Address, Addressee> addresseeMap;

    public MessageSystem() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new LinkedBlockingQueue<>());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }


    public void start() {
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            String name = "mes-queue-" + entry.getKey().getId();
            Thread thread = new Thread(() -> {
                while (true) {
                    LinkedBlockingQueue<Message> queue = messagesMap.get(entry.getKey());
                    while (true) {
                        try {
                            Message message = queue.take(); //Blocks
                            log.info("Try to process message {}", message);
                            message.exec(entry.getValue());
                        } catch (InterruptedException e) {
                            log.info("Thread interrupted. Finishing: {}", name);
                            return;
                        }
                    }
                }
            });
            thread.setName(name);
            thread.start();
            workers.add(thread);
        }
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}
