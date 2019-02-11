package ru.petrovvich.study.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class AtmImplCareTaker {

    final Deque<AtmImplMemento> mementos = new ArrayDeque<>();

    public AtmImplMemento getMemento() {
        AtmImplMemento first = mementos.pop();
        return first;
    }

    public void addMemento(AtmImplMemento atmImplMemento) {
        mementos.push(atmImplMemento);
    }
}
