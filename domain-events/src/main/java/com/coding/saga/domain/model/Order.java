package com.coding.saga.domain.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record Order(Integer id, Customer customer, String status,
                    List<OrderLineItem> lineItems, Integer totalQty,
                    BigDecimal totalAmount, BigDecimal totalTax) {
}
