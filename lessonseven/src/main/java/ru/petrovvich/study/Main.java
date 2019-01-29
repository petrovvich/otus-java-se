package ru.petrovvich.study;

import ru.petrovvich.study.model.Banknote;
import ru.petrovvich.study.model.Cell;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

    private static final Collection<Banknote> banknotes = new ArrayList<>();

    public static void main(String[] args) {
        Cell cell = new Cell(200L, banknotes);
    }
}
