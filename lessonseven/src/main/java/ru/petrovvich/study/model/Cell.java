package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.ATMResponse;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Сущность ячейки банкомата.
 * Может хранить в себе банкоты только одного номинала.
 */
public class Cell {

    private List<Banknote> banknotes;
    private Denomination denomination;
    private Integer capacity;

    public Cell(List<Banknote> banknotes, Denomination denomination) {
        this.banknotes = banknotes;
        this.denomination = denomination;
        this.capacity = banknotes.size();
    }

    public List<Banknote> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(List<Banknote> banknotes) {
        this.banknotes = banknotes;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public int getCapacity() {
        return banknotes.isEmpty() ? 0 : banknotes.size();
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public ATMResponse addBanknotes(int countToAdd, Denomination denomination) {
        if (banknotes.size() + countToAdd >= capacity) {
            return ATMResponse.TOO_MANY_BANKNOTES;
        }
        for (int i = 0; i < countToAdd; i++) {
            banknotes.add(new Banknote(denomination));
        }
        return ATMResponse.OK;
    }

    public ATMResponse getBanknotes(int countToGet) {
        if (countToGet > banknotes.size()) {
            return ATMResponse.NOT_ENOUGH_BANKNOTES;
        }
        banknotes = banknotes.subList(0, countToGet);
        return ATMResponse.OK;
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
