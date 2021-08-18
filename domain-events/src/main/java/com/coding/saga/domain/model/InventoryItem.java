package com.coding.saga.domain.model;

import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public record InventoryItem(Integer id, String sku, String name, Integer freeQty, BigDecimal price){}