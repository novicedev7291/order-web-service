package com.coding.saga.inventory;

import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
record ItemDto(String sku,String name,BigDecimal price,Integer initialQty) {}

