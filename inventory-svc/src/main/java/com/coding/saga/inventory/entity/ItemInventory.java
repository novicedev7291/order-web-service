package com.coding.saga.inventory.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "item_inventory")
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public class ItemInventory implements Auditable {
    @Id
    @Column(name = "item_id")
    private Integer itemId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "item_id")
    @Setter(AccessLevel.PROTECTED)
    private Item item;

    @Column(name = "free_qty")
    private Integer freeQty;
    @Column(name = "committed_qty")
    private Integer committedQty;

    @Embedded
    @Setter
    private Audit audit;

    public static ItemInventory initializeQty(Integer initialQty) {
        ItemInventory qty = new ItemInventory();
        qty.freeQty = initialQty;
        qty.committedQty = 0;
        return qty;
    }
}
