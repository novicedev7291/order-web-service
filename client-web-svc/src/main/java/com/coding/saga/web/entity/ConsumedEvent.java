package com.coding.saga.web.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter
@Entity
@Table(name = "consumed_events")
public class ConsumedEvent {
    @Id
    private String id;
    private String type;
    @Type(type = "text")
    private String payload;
    @Column(name = "added_on")
    private LocalDateTime addedOn;

    public ConsumedEvent() {}

    public ConsumedEvent(String id, String type, String payload, LocalDateTime occurredOn) {
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.addedOn = occurredOn;
    }
}
