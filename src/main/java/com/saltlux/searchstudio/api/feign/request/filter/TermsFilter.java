package com.saltlux.searchstudio.api.feign.request.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.FilterType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsFilter extends Filter {

    private List<String> terms;
    private String field;

    @Builder(builderMethodName = "TermsFilterBuilder")
    private TermsFilter(List<String> terms, String field) {
        super(FilterType.TERMS_FILTER.getText());
        this.terms = terms;
        this.field = field;
    }
}
