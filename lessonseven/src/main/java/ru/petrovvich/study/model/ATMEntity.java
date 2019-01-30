package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.Denomination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Базовая сущность банкомата.
 * Хранит в себе ячейки, заполненные банкнотами.
 * Одна ячейка не может содержать набор разного номинала банкнот.
 */
public class ATMEntity {

    private Collection<Cell> cells;

    public ATMEntity() {
        onCreate();
    }

    private void onCreate() {
        cells.add(new Cell(generateBanknotes(Denomination.ONE_HUNDRED), Denomination.ONE_HUNDRED));
        cells.add(new Cell(generateBanknotes(Denomination.TWO_HUNDRED), Denomination.TWO_HUNDRED));
        cells.add(new Cell(generateBanknotes(Denomination.FIVE_HUNDRED), Denomination.FIVE_HUNDRED));
        cells.add(new Cell(generateBanknotes(Denomination.ONE_THOUSAND), Denomination.ONE_THOUSAND));
        cells.add(new Cell(generateBanknotes(Denomination.TWO_THOUSAND), Denomination.TWO_THOUSAND));
        cells.add(new Cell(generateBanknotes(Denomination.FIVE_THOUSAND), Denomination.FIVE_THOUSAND));
    }

    private Collection<Banknote> generateBanknotes(Denomination denomination) {
        List<Banknote> result = new ArrayList<>();


        return null;
    }
}
