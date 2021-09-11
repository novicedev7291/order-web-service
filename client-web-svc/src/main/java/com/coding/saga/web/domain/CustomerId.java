package com.coding.saga.web.domain;

public record CustomerId(Long value) {
    public static CustomerId newId(long value) {
        return new CustomerId(value);
    }
}
