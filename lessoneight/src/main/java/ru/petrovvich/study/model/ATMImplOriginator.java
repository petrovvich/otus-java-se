package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.Currency;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.Map;
import java.util.StringJoiner;

public class ATMImplOriginator {

    private Currency currency;
    private Map<Denomination, Long> countOfCells;

    public ATMImplOriginator(Currency currency, Map<Denomination, Long> countOfCells) {
        this.currency = currency;
        this.countOfCells = countOfCells;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Map<Denomination, Long> getCountOfCells() {
        return countOfCells;
    }

    public void setCountOfCells(Map<Denomination, Long> countOfCells) {
        this.countOfCells = countOfCells;
    }

    public AtmImplMemento saveToMemento() {
        AtmImplMemento atmImplMemento = new AtmImplMemento(this.currency, this.countOfCells);
        return atmImplMemento;
    }

    public void undoFromMemento(AtmImplMemento atmImplMemento) {
        this.currency = atmImplMemento.getCurrency();
        this.countOfCells = atmImplMemento.getCountOfCells();
    }

    public void printInfo() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ATMImplOriginator.class.getSimpleName() + "[", "]")
                .add("currency=" + currency)
                .add("countOfCells=" + countOfCells)
                .toString();
    }
}
