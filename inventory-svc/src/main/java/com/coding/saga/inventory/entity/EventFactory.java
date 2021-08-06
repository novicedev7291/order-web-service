package com.coding.saga.inventory.entity;

import com.coding.saga.inventory.EventType;
import com.coding.saga.inventory.util.JsonUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventFactory {
    public static Event create(String aggregateId, String aggregateType, EventType type, Object payload) {
        Event event = new Event();
        event.setId(UUID.randomUUID().toString());
        event.setAggregateId(aggregateId);
        event.setAggregateType(aggregateType);
        event.setType(type.name());
        event.setPayload(JsonUtil.jsonString(payload));
        event.setOccurredOn(LocalDateTime.now());
        return event;
    }
}
