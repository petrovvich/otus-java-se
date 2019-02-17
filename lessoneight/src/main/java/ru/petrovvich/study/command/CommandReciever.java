package ru.petrovvich.study.command;

import java.util.ArrayList;
import java.util.List;

public class CommandReciever {
    private List<Command> listCommands = new ArrayList();

    public void addCommand(Command command) {
        listCommands.add(command);
    }

    public void run() {
        for (Command command : listCommands) {
            command.execute();
        }
    }
}
