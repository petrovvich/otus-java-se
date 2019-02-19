package ru.petrovvich.study.model;

import ru.petrovvich.study.model.enums.ATMResponse;

public interface ATM {

    int DEFAULT_CAPACITY = 100;

    void onCreate();

    void onReload();

    boolean onRestore();

    ATMResponse getCash(Integer summ);

    ATMResponse putCash(Integer sum);

    Integer getTotalBalanceInUnits();
}
