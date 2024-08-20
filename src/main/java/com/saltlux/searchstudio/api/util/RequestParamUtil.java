package com.saltlux.searchstudio.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestParamUtil {

    private static final ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return objectMapper;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertToMap(Object request) {
        return objectMapper.convertValue(request, Map.class);
    }
}
