package com.coding.saga.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka")
public class ConsumerProperties {
    private String bootstrapServers;
    private String schemaRegistryUrl;
}
