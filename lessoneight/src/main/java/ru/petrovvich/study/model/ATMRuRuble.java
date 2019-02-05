package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.ATMResponse;
import ru.petrovvich.study.model.enums.Currency;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.TreeMap;

/**
 * Базовая сущность банкомата.
 * Хранит в себе ячейки, заполненные банкнотами.
 * Одна ячейка не может содержать набор разного номинала банкнот.
 */
public class ATMRuRuble implements ATM {

    private List<Cell> cells = new ArrayList<>();
    private Currency currency;

    public ATMRuRuble(Currency currency) {
        this.currency = currency;
        onCreate();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public boolean addCell(Cell cell) {
        return this.cells.add(cell);
    }

    /**
     * Метод наполняющий банкомат ячейками и купюрами всех номиналов при создании.
     */
    @Override
    public void onCreate() {
        for (Denomination d : Denomination.values()) {
            createAndPutCell(d);
        }
    }

    /**
     * Метод использующийся при наполнении банкомата купюрами.
     */
    @Override
    public void onReload() {
        clearCells();
        onCreate();
    }

    /**
     * Метод для получения денег из банкомата
     *
     * @param summ сумма для получения
     * @return результат операции
     */
    public ATMResponse getCash(Integer summ) {
        ATMResponse response = ATMResponse.ERROR;
        if (summ > getTotalBalanceInUnits()) {
            return ATMResponse.NOT_ENOUGH_BALANCE;
        }
        TreeMap<Integer, Integer> balances = getTotalBalance();

        for (Integer i : balances.descendingKeySet()) {
            if (i <= summ) {
                int count = summ / i;
                if (count == 0) {
                    continue;
                }
                summ -= count * i;
                Cell cell = getCellByNominal(i);
                response = cell.getBanknotes(count);
                if (!response.equals(ATMResponse.OK)) {
                    return response;
                }
            }
            if (summ == 0) {
                return ATMResponse.OK;
            }
            if (summ < 0) {
                return ATMResponse.NOT_ENOUGH_BALANCE;
            }
        }

        if (summ > 0) {
            return ATMResponse.INVALID_SUM;
        }


        return response;
    }

    /**
     * Метод для внесения средств в банкомат
     *
     * @param sum сумма для внесения
     * @return результат выполнения операции
     */
    public ATMResponse putCash(Integer sum) {
        TreeMap<Integer, Integer> balances = getTotalBalance();
        ATMResponse response = ATMResponse.ERROR;
        for (Integer i : balances.descendingKeySet()) {
            Cell cell = getCellByNominal(i);
            if (i <= sum) {
                int count = sum / i;
                if (count == 0) {
                    continue;
                }
                sum -= count * i;

                response = cell.addBanknotes(count);
                if (!response.equals(ATMResponse.OK)) {
                    return response;
                }
            }
            if (sum == 0) {
                return ATMResponse.OK;
            }
        }

        if (sum > 0) {
            return ATMResponse.INVALID_SUM;
        }

        return response;
    }

    /**
     * Метод для расчета сотатка в банкомате единицах валюты
     *
     * @return остаток в единицах валюты
     */
    public Integer getTotalBalanceInUnits() {
        int result = 0;

        for (Cell cell : cells) {
            result += cell.getBalance();
        }
        return result;
    }

    /**
     * Метод для подсчета остатка в банкомате по каждой из ячеек.
     *
     * @return мапа с перечислением номиналов и остатков по каждому из номиналов
     */
    private TreeMap<Integer, Integer> getTotalBalance() {
        TreeMap<Integer, Integer> balances = new TreeMap<>();

        for (Cell cell : cells) {
            balances.put(cell.getDenomination().getNominal(), cell.getBalance());
        }
        return balances;
    }

    private Cell getCellByNominal(Integer nominal) {
        Cell result = null;
        for (Cell cell : cells) {
            if (cell.getDenomination().getNominal() == nominal) {
                result = cell;
            }
        }
        return result;
    }

    private void createAndPutCell(Denomination nominal) {
        Cell cell = new Cell(nominal, DEFAULT_CAPACITY);
        cells.add(cell);
    }

    private void clearCells() {
        cells = null;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ATMRuRuble.class.getSimpleName() + "[", "]")
                .add("cells=" + cells)
                .add("currency=" + currency)
                .toString();
    }
}
