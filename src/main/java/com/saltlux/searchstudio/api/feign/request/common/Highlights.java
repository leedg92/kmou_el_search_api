package com.saltlux.searchstudio.api.feign.request.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Highlights {

    private Summary summary;

    @Builder
    private Highlights(Summary summary) {
        this.summary = summary;
    }
}
