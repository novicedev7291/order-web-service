package com.coding.saga.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Embeddable
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class OrderItem implements Serializable {
    @Column(name = "item_id")
    private Integer itemId;

    private BigDecimal price;

    private Integer qty;

    @Column(name = "total_amount`")
    private BigDecimal totalAmount;

    @Column(name = "total_tax")
    private BigDecimal totalTax;
}
