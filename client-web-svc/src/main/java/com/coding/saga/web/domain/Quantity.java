package com.coding.saga.web.domain;

public record Quantity(Integer value) {
    public static Quantity of(int value) {
        return new Quantity(value);
    }

    Quantity add(Quantity quantity) {
        return Quantity.of(this.value + quantity.value());
    }
}
