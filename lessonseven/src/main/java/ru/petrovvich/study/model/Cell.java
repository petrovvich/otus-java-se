package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.Denomination;

import java.util.Collection;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Сущность ячейки банкомата.
 * Может хранить в себе банкоты только одного номинала.
 */
public class Cell {

    private Collection<Banknote> banknotes;
    private Denomination denomination;
    private Integer capacity;

    public Cell(Collection<Banknote> banknotes, Denomination denomination) {
        this.banknotes = banknotes;
        this.denomination = denomination;
        this.capacity = banknotes.size();
    }

    public int getCapacity() {
        return banknotes.isEmpty() ? 0 : banknotes.size();
    }

    public Collection<Banknote> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(Collection<Banknote> banknotes) {
        this.banknotes = banknotes;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cell.class.getSimpleName() + "[", "]")
                .add("banknotes=" + banknotes)
                .add("denomination=" + denomination)
                .add("capacity=" + capacity)
                .add(UUID.randomUUID().toString())
                .toString();
    }
}
