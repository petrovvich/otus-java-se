package ru.petrovvich.study.model.enums;

/**
 * Номинал банкнот, возможных в обращении в банкомате
 */
public enum Denomination {

    ONE_HUNDRED(100, "сто"),
    TWO_HUNDRED(200, "двести"),
    FIVE_HUNDRED(500, "триста"),
    ONE_THOUSAND(1000, "тысяча"),
    TWO_THOUSAND(2000, "две тысячи"),
    FIVE_THOUSAND(5000, "пять тысяч");

    Denomination(Integer nominal, String description) {
        this.nominal = nominal;
        this.description = description;
    }

    private Integer nominal;
    private String description;

    public Integer getNominal() {
        return nominal;
    }

    public String getDescription() {
        return description;
    }
}
