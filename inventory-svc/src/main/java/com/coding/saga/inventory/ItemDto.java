package com.coding.saga.inventory;

import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record ItemDto(String sku,String name,BigDecimal price,Integer initialQty) {}

