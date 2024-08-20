package com.saltlux.searchstudio.api.feign.request.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueryType {

    BOOL_QUERY("boolQuery"),
    HYBRID_QUERY("hybridQuery"),
    PREFIX_QUERY("prefixQuery"),
    RANGE_QUERY("rangeQuery"),
    SYNTAX_QUERY("syntaxQuery"),
    TERM_QUERY("termQuery"),
    TERMS_QUERY("termsQuery"),
    VECTOR_QUERY("vectorQuery");

    private final String text;
}
