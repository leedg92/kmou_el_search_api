package com.saltlux.searchstudio.api.feign.request.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Summary {

    private List<String> fields;
    private int maxChars;
    private String headTag;
    private String tailTag;

    @Builder
    private Summary(List<String> fields, int maxChars, String headTag, String tailTag) {
        this.fields = fields;
        this.maxChars = maxChars;
        this.headTag = headTag;
        this.tailTag = tailTag;
    }
}
