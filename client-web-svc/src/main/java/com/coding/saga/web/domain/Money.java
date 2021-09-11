package com.coding.saga.web.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record Money(BigDecimal value) {
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    public static final int DEFAULT_SCALE = 6;

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    Money multiply(Money other) {
        BigDecimal thisScaledValue = value.setScale(DEFAULT_SCALE, RoundingMode.HALF_EVEN);
        BigDecimal otherScaledValue = other.value.setScale(DEFAULT_SCALE, RoundingMode.HALF_EVEN);
        return new Money(thisScaledValue.multiply(otherScaledValue));
    }

    Money add(Money other) {
        BigDecimal thisScaledValue = value.setScale(DEFAULT_SCALE, RoundingMode.HALF_EVEN);
        BigDecimal otherScaledValue = other.value.setScale(DEFAULT_SCALE, RoundingMode.HALF_EVEN);
        return new Money(thisScaledValue.add(otherScaledValue));
    }

}
