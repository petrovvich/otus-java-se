package ru.petrovvich.study.command;

import ru.petrovvich.study.model.ATM;

public class ReloadCommand implements Command {

    private ATM atm;

    public ReloadCommand(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.onReload();
    }
}
