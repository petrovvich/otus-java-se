package ru.petrovvich.study.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сущность для хранения множества банкоматов.
 */
public class ATMDepartment {

    private List<ATM> atms;

    public ATMDepartment() {
        this.atms = new ArrayList<>();
    }

    public ATMDepartment(List<ATM> atms) {
        this.atms = atms;
    }

    /**
     * Метод возвращает к исходному состоянию все банкоматы
     */
    public void resetAll() {
        atms.forEach(ATM::onReload);
    }

    /**
     * Метод для выбора балансов всех банкоматов.
     *
     * @return коллекцию с перечислением банкоматов и балансов к каждому из них
     */
    public Map<ATM, Integer> getBalances() {
        Map<ATM, Integer> result = new HashMap<>();
        if (atms != null) {
            atms.forEach(atm -> result.put(atm, atm.getTotalBalanceInUnits()));
        }
        return result;
    }

}
