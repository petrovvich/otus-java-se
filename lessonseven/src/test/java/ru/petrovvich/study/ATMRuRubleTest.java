package ru.petrovvich.study;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.petrovvich.study.model.ATMRuRuble;
import ru.petrovvich.study.model.enums.ATMResponse;
import ru.petrovvich.study.model.enums.Currency;

public class ATMRuRubleTest {

    private ATMRuRuble atmRuRuble;
    private int balance;

    @Before
    public void setUp() {
        this.atmRuRuble = new ATMRuRuble(Currency.RUR);
        this.balance = atmRuRuble.getTotalBalanceInUnits();
    }

    @Test
    public void testGet100Cash() {
        Integer amountToGet = 100;

        atmRuRuble.getCash(amountToGet);

        int actualCash = atmRuRuble.getTotalBalanceInUnits();

        Assert.assertEquals(balance - amountToGet, actualCash);
    }

    @Test
    public void testPut100CashInFullATM() {
        Assert.assertEquals(ATMResponse.TOO_MANY_BANKNOTES, atmRuRuble.putCash(100));
    }

    @Test
    public void testGetInvalidAmount() {
        Assert.assertEquals(ATMResponse.INVALID_SUM, atmRuRuble.putCash(65));
    }
}
