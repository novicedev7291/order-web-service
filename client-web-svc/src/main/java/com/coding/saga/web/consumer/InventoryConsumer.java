package com.coding.saga.web.consumer;

import com.coding.saga.domain.model.InventoryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Service
@Slf4j
public class InventoryConsumer {

    @KafkaListener(topics = "inventory.events")
    public void onInventoryChanges(@Payload InventoryItem detail,
                                   @Header("id") String id,
                                   @Header("type") String eventType) {
        log.info(detail.toString());
        log.info("Headers -> id:{}, Event Type:{}", id, eventType);
    }
}
