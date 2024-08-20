package com.saltlux.searchstudio.api.feign.request.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Query {

    public String queryType;

    public Query(String queryType) {
        this.queryType = queryType;
    }
}
