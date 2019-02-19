package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.ATMResponse;
import ru.petrovvich.study.model.enums.Currency;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.StringJoiner;
import java.util.UUID;

/**
 * Сущность ячейки банкомата.
 * Может хранить в себе банкоты только одного номинала.
 */
public class Cell {

    private Denomination denomination;
    private Integer capacity;
    private Integer balance;
    private Integer currentCapacity;

    public Cell(Denomination denomination, int capacity) {
        this.denomination = denomination;
        this.capacity = capacity;
        this.currentCapacity = capacity;
        this.balance = capacity * denomination.getNominal();
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public int getBalance() {
        return balance;
    }

    public ATMResponse addBanknotes(int countToAdd) {
        if (currentCapacity + countToAdd >= capacity) {
            return ATMResponse.TOO_MANY_BANKNOTES;
        }
        currentCapacity += countToAdd;
        balance += (denomination.getNominal() * countToAdd);

        return ATMResponse.OK;
    }

    public ATMResponse getBanknotes(int countToGet) {
        if (countToGet > currentCapacity) {
            return ATMResponse.NOT_ENOUGH_BANKNOTES;
        }
        currentCapacity -= countToGet;
        balance -= (denomination.getNominal() * countToGet);

        return ATMResponse.OK;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cell.class.getSimpleName() + "[", "]")
                .add("denomination=" + denomination)
                .add("capacity=" + capacity)
                .add("balance=" + balance)
                .add(UUID.randomUUID().toString())
                .toString();
    }
}
