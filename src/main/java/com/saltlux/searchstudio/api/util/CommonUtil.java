package com.saltlux.searchstudio.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.saltlux.searchstudio.api.feign.request.enums.Occur;
import com.saltlux.searchstudio.api.feign.request.filter.BoolFilter;
import com.saltlux.searchstudio.api.feign.request.filter.Filter;
import com.saltlux.searchstudio.api.feign.request.query.BoolQuery;
import com.saltlux.searchstudio.api.feign.request.query.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CommonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.INDENT_OUTPUT);

    public static <T> void printRequestLog(String message, T request) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            log.debug("{} -> {}", message, requestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> convertMap(Map<String, List<String>> originalMap) {
        Map<String, Object> convertedMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            List<String> valueList = entry.getValue();
            // 단일 값인 경우 해당 값을, 그렇지 않으면 리스트 그대로 사용
            Object value = (valueList.size() == 1) ? StringUtils.defaultIfBlank(valueList.get(0), "") : valueList;

            convertedMap.put(key, value);
        }

        return convertedMap;
    }

    public static boolean isPresentAndNotBlank(Map<String, Object> request, String key) {
        Object value = request.get(key);
        return value != null && StringUtils.isNotBlank(value.toString());
    }

}
