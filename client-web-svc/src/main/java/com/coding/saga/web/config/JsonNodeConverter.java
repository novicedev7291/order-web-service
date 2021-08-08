package com.coding.saga.web.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.converter.JsonMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class JsonNodeConverter extends JsonMessageConverter {
    @Override
    protected Object extractAndConvertValue(ConsumerRecord<?, ?> r, Type type) {
        Object value = r.value();
        if (!(value instanceof TextNode)) {
            return super.extractAndConvertValue(r, type);
        }
        try{
            TextNode node = (TextNode) value;
            JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            return getObjectMapper().readValue(node.asText(), javaType);
        }catch (IOException ex) {
            throw new ConversionException("Failed to convert from JSON", r, ex);
        }
    }
}
