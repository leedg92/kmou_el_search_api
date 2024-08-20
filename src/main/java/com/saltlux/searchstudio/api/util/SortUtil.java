package com.saltlux.searchstudio.api.util;

import com.saltlux.searchstudio.api.feign.request.common.Sort;
import com.saltlux.searchstudio.api.feign.request.enums.Order;
import com.saltlux.searchstudio.api.feign.request.enums.SortType;

public class SortUtil {

    public static Sort createSort(Sort sort) {
        if (sort == null) {
            return Sort.builder()
                .sortType(SortType.SCORE_SORT.getText())
                .order(Order.DESC)
                .build();
        }
        return sort;
    }
}
