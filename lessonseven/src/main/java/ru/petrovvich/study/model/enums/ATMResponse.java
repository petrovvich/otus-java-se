package ru.petrovvich.study.model.enums;

public enum ATMResponse {

    OK("Операция проведена успешно"),
    TOO_MANY_BANKNOTES("Недостаточно места для банкнот указанного номинала для проведения операции"),
    NOT_ENOUGH_BANKNOTES("Недостаточно банкнот указанного номинала для проведения операции"),
    NOT_ENOUGH_BALANCE("Недостаточно средств для проведения операции");

    ATMResponse(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
