package com.coding.saga.inventory.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "items")
@EqualsAndHashCode(of = "sku", callSuper = false)
@EntityListeners(AuditListener.class)
public class Item extends AbstractEntity<Integer> implements Auditable{
    private String sku;
    private String name;
    private BigDecimal price;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "item")
    private ItemInventory inventory;

    @Embedded
    @Setter
    private Audit audit;

    public void setQuantity(Integer qty) {
        ItemInventory inv = ItemInventory.initializeQty(qty);
        inv.setItem(this);
        this.inventory = inv;
    }

    public static Item of(String sku, String name, BigDecimal price) {
        Item item = new Item();
        item.sku = sku;
        item.name = name;
        item.price = price;
        return item;
    }
}

