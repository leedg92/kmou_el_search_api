package com.saltlux.searchstudio.api.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelatedKeywordsRequest {

    /**
     * 검색어
     */
    @NotBlank
    private String keyword;
    /**
     * 시작날짜
     */
    @NotBlank
    private String startDate;
    /**
     * 종료날짜
     */
    @NotBlank
    private String endDate;

    @Builder
    private RelatedKeywordsRequest(String keyword, String startDate, String endDate) {
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
