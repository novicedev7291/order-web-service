package com.coding.saga.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter(PROTECTED)
@Entity
@Table(name = "events")
public class Event {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(name = "aggregate_id")
    private String aggregateId;

    @Column(name = "aggregate_type")
    private String aggregateType;

    private String type;

    @Type(type = "text")
    private String payload;

    @Column(name = "occurred_on")
    private LocalDateTime occurredOn;

    @Version
    private Integer version;
}
