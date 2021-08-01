package com.coding.saga.inventory.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public class AuditListener {
    @PrePersist
    public void onCreate(Auditable auditable) {
        Audit audit = auditable.getAudit();

        if(audit == null) {
            audit = new Audit();
        }

        audit.setCreatedOn(LocalDateTime.now());
        audit.setUpdatedOn(LocalDateTime.now());

        auditable.setAudit(audit);
    }

    @PreUpdate
    public void onUpdate(Auditable auditable) {
        Audit audit = auditable.getAudit();
        audit.setUpdatedOn(LocalDateTime.now());
    }
}
