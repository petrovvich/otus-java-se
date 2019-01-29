package ru.petrovvich.study.model;

import java.util.Collection;

public class Cell {

    private Long capacity;
    private Collection<Banknote> banknotes;

    public Cell(Long capacity, Collection<Banknote> banknotes) {
        this.capacity = capacity;
        this.banknotes = banknotes;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Collection<Banknote> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(Collection<Banknote> banknotes) {
        this.banknotes = banknotes;
    }
}
