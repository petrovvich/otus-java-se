package ru.petrovvich.study.model.enums;

public enum Currency {

    RUR("Российский рубль"),
    USD("Доллар США");

    Currency(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
