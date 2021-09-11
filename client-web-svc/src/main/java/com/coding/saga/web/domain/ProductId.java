package com.coding.saga.web.domain;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record ProductId(Long value) {
    public static ProductId newId(long value) {
        return new ProductId(value);
    }
}
