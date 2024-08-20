package com.saltlux.searchstudio.api.feign.request.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilterType {

    TERM_FILTER("termFilter"),
    TERMS_FILTER("termsFilter"),
    RANGE_FILTER("rangeFilter"),
    NOT_FILTER("notFilter"),
    BOOL_FILTER("boolFilter");

    private final String text;
}
