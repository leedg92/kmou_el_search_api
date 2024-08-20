package com.saltlux.searchstudio.api.feign.request.query;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PrefixQuery extends Query {

    private String term;
    private String field;
    private Double boost;

    @Builder(builderMethodName = "PrefixQueryBuilder")
    private PrefixQuery(String term, String field, Double boost) {
        super(QueryType.PREFIX_QUERY.getText());
        this.term = term;
        this.field = field;
        this.boost = boost;
    }
}
