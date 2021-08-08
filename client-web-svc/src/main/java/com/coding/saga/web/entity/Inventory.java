package com.coding.saga.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    private Integer id;

    private String sku;
    private String name;
    @Column(name = "free_qty")
    private Integer freeQty;
    private BigDecimal price;
}
