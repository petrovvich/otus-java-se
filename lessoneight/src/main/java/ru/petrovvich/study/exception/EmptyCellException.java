package ru.petrovvich.study.exception;

public class EmptyCellException extends Throwable {

    public EmptyCellException() {
        super("Cell are empty");
    }
}
