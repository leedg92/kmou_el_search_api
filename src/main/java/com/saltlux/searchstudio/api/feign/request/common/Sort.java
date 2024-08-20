package com.saltlux.searchstudio.api.feign.request.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sort {

    private String sortType;
    private String field;
    private Order order;

    @Builder
    private Sort(String sortType, String field, Order order) {
        this.sortType = sortType;
        this.field = field;
        this.order = order;
    }
}
