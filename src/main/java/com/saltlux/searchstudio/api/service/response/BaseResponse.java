package com.saltlux.searchstudio.api.service.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponse {

    private Object result;
    private int totalCount;

    @Builder
    private BaseResponse(Object result, int totalCount) {
        this.result = result;
        this.totalCount = totalCount;
    }
}
