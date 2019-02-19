package ru.petrovvich.study.command;

import ru.petrovvich.study.model.ATM;

public class CreateCommand implements Command {

    private ATM atm;

    public CreateCommand(ATM atm) {
        this.atm = atm;
    }
    @Override
    public void execute() {
        atm.onCreate();
    }
}
