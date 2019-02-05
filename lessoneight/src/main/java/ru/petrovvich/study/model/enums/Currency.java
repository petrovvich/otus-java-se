package ru.petrovvich.study.model.enums;

/**
 * Справочник валют, возможных к выдаче в банкомате
 */
public enum Currency {

    RUR("Российский рубль"),
    USD("Доллар США"),
    EUR("Евро");

    Currency(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
