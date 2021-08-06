package com.coding.saga.inventory;

import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record ItemPayload(Integer id, String sku,String name,Integer qty,BigDecimal price) {
}