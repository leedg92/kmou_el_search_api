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
public class NotFilter extends Filter {

    private Filter filter;

    @Builder(builderMethodName = "NotFilterBuilder")
    private NotFilter(Filter filter) {
        super(FilterType.NOT_FILTER.getText());
        this.filter = filter;
    }
}
