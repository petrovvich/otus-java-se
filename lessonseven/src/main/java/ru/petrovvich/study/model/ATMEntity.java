package ru.petrovvich.study.model;

import ru.petrovvich.study.exception.EmptyCellException;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Базовая сущность банкомата.
 * Хранит в себе ячейки, заполненные банкнотами.
 * Одна ячейка не может содержать набор разного номинала банкнот.
 */
public class ATMEntity {

    private static final long DEFAULT_CAPACITY = 100;

    private List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public ATMEntity() {
        onCreate();
    }

    /**
     * Метод для подсчета остатка в банкомате
     *
     * @return мапа с перечислением номиналов и остатков по каждому из номиналов
     */
    public Map<String, Long> getTotalBalance() {
        Map<String, Long> result = new HashMap<>();

        if (cells == null || cells.isEmpty()) {
            try {
                throw new EmptyCellException();
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
        }
        cells.forEach(cell -> result.put(cell.getDenomination().getDescription(), (long) cell.getBanknotes().size()));
        return result;
    }

    /**
     * Метод для расчета сотатка в банкомате единицах валюты
     *
     * @return остаток в единицах валюты
     */
    public Long getTotalBalanceInUnits() {
        AtomicLong result = new AtomicLong(0L);

        cells.forEach(cell -> result.updateAndGet(v -> v + (long) cell.getBanknotes().size() * cell.getDenomination().getNominal()));

        return result.get();
    }



    public boolean getCash(Long summ) {
        if (summ > getTotalBalanceInUnits()) {
            return false;
        }

        return true;
    }

    public void putCash() {

    }

    /**
     * Метод использующийся при наполнении банкомата купюрами.
     */
    public void onReload() {
        clearCells();
        onCreate();
    }

    /**
     * Метод наполняющий банкомат ячейками и купюрами всех номиналов при создании.
     */
    private void onCreate() {
        for (Denomination d : Denomination.values()) {
            createAndPutCell(d);
        }
    }

    private void createAndPutCell(Denomination nominal) {
        Cell cell = new Cell(Objects.requireNonNull(createBanknotes(nominal)), nominal);
        cells.add(cell);
    }

    private List<Banknote> createBanknotes(Denomination denomination) {
        List<Banknote> banknotes = new ArrayList<>();
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            banknotes.add(new Banknote(denomination));
        }
        return banknotes;
    }

    private void clearCells() {
        cells = null;
    }


}
