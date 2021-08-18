package com.coding.saga.order.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String jsonString(Object value) {
        try{
            return mapper.writeValueAsString(value);
        }catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
