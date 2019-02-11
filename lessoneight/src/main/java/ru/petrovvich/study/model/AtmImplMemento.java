package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.Currency;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.Map;
import java.util.StringJoiner;

public class AtmImplMemento {

    private Currency currency;
    private Map<Denomination, Long> countOfCells;

    public AtmImplMemento(Currency currency, Map<Denomination, Long> countOfCells) {
        this.currency = currency;
        this.countOfCells = countOfCells;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Map<Denomination, Long> getCountOfCells() {
        return countOfCells;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AtmImplMemento.class.getSimpleName() + "[", "]")
                .add("currency=" + currency)
                .add("countOfCells=" + countOfCells)
                .toString();
    }
}
