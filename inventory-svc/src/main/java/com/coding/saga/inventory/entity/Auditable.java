package com.coding.saga.inventory.entity;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public interface Auditable {
    void setAudit(Audit audit);
    Audit getAudit();
}
