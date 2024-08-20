package com.saltlux.searchstudio.api.feign.request.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.DataType;
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
public class RangeFilter extends Filter {

    private String field;
    private DataType type;
    private String lower;
    private String upper;
    private Boolean lower_inclusive;
    private Boolean upper_inclusive;

    @Builder(builderMethodName = "RangeFilterBuilder")
    private RangeFilter(String field, DataType type, String lower, String upper, Boolean lower_inclusive,
        Boolean upper_inclusive) {
        super(FilterType.RANGE_FILTER.getText());
        this.field = field;
        this.type = type;
        this.lower = lower;
        this.upper = upper;
        this.lower_inclusive = lower_inclusive;
        this.upper_inclusive = upper_inclusive;
    }
}
