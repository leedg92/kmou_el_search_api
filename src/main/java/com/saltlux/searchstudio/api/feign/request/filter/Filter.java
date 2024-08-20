package com.saltlux.searchstudio.api.feign.request.filter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Filter {

    public String filterType;

    public Filter(String filterType) {
        this.filterType = filterType;
    }
}
