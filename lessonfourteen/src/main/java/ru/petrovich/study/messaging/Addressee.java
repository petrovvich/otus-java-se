package ru.petrovich.study.messaging;

public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
