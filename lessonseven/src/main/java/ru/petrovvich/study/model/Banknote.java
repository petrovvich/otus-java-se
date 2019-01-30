package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.Currency;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Сущность банкноты.
 * Хранит в себе уникальный номер банкноты (для простоты взята генерация UUID библиотечным методом), валюту и номинал.
 */
public class Banknote {

    private Currency currency;
    private Denomination nominal;
    private String banknote_id;

    public Banknote(Denomination nominal, Currency currency, String banknote_id) {
        this.nominal = nominal;
        this.currency = currency;
        this.banknote_id = banknote_id;
    }

    public Banknote(Denomination nominal) {
        this.nominal = nominal;
        this.currency = Currency.RUR;
        this.banknote_id = UUID.randomUUID().toString();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Denomination getNominal() {
        return nominal;
    }

    public void setNominal(Denomination nominal) {
        this.nominal = nominal;
    }

    public String getBanknote_id() {
        return banknote_id;
    }

    public void setBanknote_id(String banknote_id) {
        this.banknote_id = banknote_id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Banknote.class.getSimpleName() + "[", "]")
                .add("currency=" + currency)
                .add("nominal=" + nominal)
                .add("banknote_id='" + banknote_id + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Banknote)) return false;
        Banknote banknote = (Banknote) o;
        return currency == banknote.currency &&
                nominal == banknote.nominal &&
                Objects.equals(banknote_id, banknote.banknote_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, nominal, banknote_id);
    }
}
