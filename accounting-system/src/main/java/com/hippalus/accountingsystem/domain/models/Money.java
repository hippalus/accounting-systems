package com.hippalus.accountingsystem.domain.models;


import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public final class Money implements Comparable<Money> {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value;
    }

    public static Money of(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money value cannot be less than zero!");
        }
        return new Money(value);
    }

    public Money add(Money amount) {
        Objects.requireNonNull(amount);
        if (amount.isZero()) {
            return this;
        }
        return new Money(this.value.add(amount.getValue()));
    }

    @Override
    public int compareTo(Money money) {
        Objects.requireNonNull(money);
        return this.getValue().compareTo(money.getValue());
    }
    @Transient
    public boolean isZero() {
        return this.equals(ZERO);
    }

    public boolean isEqualTo(double amount) {
        return this.getValue().compareTo(BigDecimal.valueOf(amount)) == 0;
    }

    public boolean isLessThanOrEquals(Money amount) {
        Objects.requireNonNull(amount);
        return this.compareTo(amount) <= 0;
    }

    public boolean isLessThan(Money amount) {
        Objects.requireNonNull(amount);
        return this.compareTo(amount) < 0;
    }

    public boolean isGreaterThan(Money amount) {
        Objects.requireNonNull(amount);
        return this.compareTo(amount) > 0;
    }

    public boolean isGreaterThanOrEquals(Money amount) {
        Objects.requireNonNull(amount);
        return this.compareTo(amount) >= 0;
    }


}