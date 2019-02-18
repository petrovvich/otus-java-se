package ru.petrovvich.study.command;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
    private List<Command> listCommands = new ArrayList<>();

    public void addCommand(Command command) {
        listCommands.add(command);
    }

    public void execute() {
        listCommands.forEach(Command::execute);
    }
}
