package com.coding.saga.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter
public class OrderItemDto {
    private Integer itemId;
    private Integer qty;
    private BigDecimal price;
}
