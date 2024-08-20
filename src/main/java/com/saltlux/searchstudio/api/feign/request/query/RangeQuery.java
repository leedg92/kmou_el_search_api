package com.saltlux.searchstudio.api.feign.request.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.DataType;
import com.saltlux.searchstudio.api.feign.request.enums.QueryType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RangeQuery extends Query {

    private String field;
    private Double boost;
    private DataType type;
    private String lower;
    private String upper;
    private Boolean lower_inclusive;
    private Boolean upper_inclusive;

    @Builder(builderMethodName = "RangeQueryBuilder")
    private RangeQuery(String field, Double boost, DataType type, String lower, String upper, Boolean lower_inclusive,
        Boolean upper_inclusive) {
        super(QueryType.RANGE_QUERY.getText());
        this.field = field;
        this.boost = boost;
        this.type = type;
        this.lower = lower;
        this.upper = upper;
        this.lower_inclusive = lower_inclusive;
        this.upper_inclusive = upper_inclusive;
    }
}
