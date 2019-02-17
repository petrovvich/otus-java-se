package ru.petrovvich.study.command;

import ru.petrovvich.study.model.ATM;

public class RestoreCommand implements Command {

    private ATM atm;

    public RestoreCommand(ATM atm) {
        this.atm = atm;
    }
    @Override
    public void execute() {
        atm.onRestore();
    }
}
