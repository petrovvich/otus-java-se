package ru.petrovvich.study.model.enums;

/**
 * Номинал банкнот, возможных в обращении в банкомате
 */
public enum Denomination {

    ONE_HUNDRED("Сто"),
    TWO_HUNDRED("Двести"),
    FIVE_HUNDRED("Пятьсот"),
    ONE_THOUSAND("Тысяча"),
    TWO_THOUSAND("Две тысячи"),
    FIVE_THOUSAND("Пять тысяч");

    Denomination(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
