package com.coding.saga.inventory.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter(PROTECTED)
@Entity
@Table(name = "events")
public class Event extends AbstractEntity<String> {
    @Column(name = "aggregate_id")
    private String aggregateId;

    @Column(name = "aggregate_type")
    private String aggregateType;

    private String type;

    private String payload;

    @Column(name = "occurred_on")
    private LocalDateTime occurredOn;
}
