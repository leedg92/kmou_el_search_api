package com.saltlux.searchstudio.api.feign.request.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.FilterType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermFilter extends Filter {

    private String term;
    private String field;

    @Builder(builderMethodName = "TermFilterBuilder")
    private TermFilter(String term, String field) {
        super(FilterType.TERM_FILTER.getText());
        this.term = term;
        this.field = field;
    }
}
