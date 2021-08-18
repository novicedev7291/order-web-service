package com.coding.saga.domain.model;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record Customer(
        String name,
        String shipAddress1,
        String shipAddress2,
        String shipCity,
        String shipState,
        String shipCountry,
        String phone
        ) {
}
