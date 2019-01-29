package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.Currency;

public class Banknote {

    private Currency currency;
    private Long nominal;

    public Banknote(Long nominal, Currency currency) {
        this.nominal = nominal;
        this.currency = currency;
    }

    public Banknote(Long nominal) {
        this.nominal = nominal;
        this.currency = Currency.RUR;
    }
}
