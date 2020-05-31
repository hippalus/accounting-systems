package com.hippalus.accountingsystem.application.mappers;

import com.hippalus.accountingsystem.domain.models.Money;

import java.math.BigDecimal;

public interface MoneyMapper {

    static BigDecimal moneyToDecimal(Money money) {
        return money.getValue();
    }

    static Money decimalToMoney(BigDecimal decimal) {
        return Money.of(decimal);
    }
}

