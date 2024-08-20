package com.saltlux.searchstudio.api.feign.request.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.FilterType;
import com.saltlux.searchstudio.api.feign.request.enums.Occur;
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
public class BoolFilter extends Filter {

    private List<Clauses> clauses;

    @Builder(builderMethodName = "BoolFilterBuilder")
    public BoolFilter(List<Clauses> clauses) {
        super(FilterType.BOOL_FILTER.getText());
        this.clauses = clauses;
    }

    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Clauses {

        private Filter filter;
        private Occur occur;

        @Builder
        private Clauses(Filter filter, Occur occur) {
            this.filter = filter;
            this.occur = occur;
        }
    }
}
