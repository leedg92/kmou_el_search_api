package com.saltlux.searchstudio.api.feign.request.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {

    FIELD_SORT("fieldSort"),
    SCORE_SORT("scoreSort");

    private final String text;
}
