package com.coding.saga.domain.model;

import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record OrderLineItem(Integer itemId, Integer qty, BigDecimal price, BigDecimal totalAmount, BigDecimal totalTax) {
}
