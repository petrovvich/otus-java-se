package ru.petrovvich.study;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.petrovvich.study.model.ATMImpl;
import ru.petrovvich.study.model.enums.ATMResponse;
import ru.petrovvich.study.model.enums.Currency;
import ru.petrovvich.study.model.enums.Denomination;

import java.util.HashMap;
import java.util.Map;

public class ATMImplTest {

    private ATMImpl atmImpl;
    private int balance;

    @Before
    public void setUp() {
        Map<Denomination, Long> cells = new HashMap<>();
        cells.put(Denomination.FIVE_HUNDRED, 10L);
        cells.put(Denomination.ONE_HUNDRED, 10L);
        this.atmImpl = new ATMImpl(Currency.RUR, cells);
        this.balance = atmImpl.getTotalBalanceInUnits();
    }

    @Test
    public void testGet100Cash() {
        Integer amountToGet = 100;

        atmImpl.getCash(amountToGet);

        int actualCash = atmImpl.getTotalBalanceInUnits();

        Assert.assertEquals(balance-amountToGet, actualCash);
    }

    @Test
    public void testPut100CashInFullATM() {
        Assert.assertEquals(ATMResponse.TOO_MANY_BANKNOTES, atmImpl.putCash(100));
    }

    @Test
    public void testGetInvalidAmount() {
        Assert.assertEquals(ATMResponse.INVALID_SUM, atmImpl.putCash(65));
    }
}
