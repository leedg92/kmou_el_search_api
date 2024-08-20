package com.saltlux.searchstudio.api.feign.request.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Field {

    _SEARCH_MORPH("_search_morph"),
    _SEARCH_BIGRAM("_search_bigram"),
    _SEARCH("_search"),
    ;

    private final String text;
}
