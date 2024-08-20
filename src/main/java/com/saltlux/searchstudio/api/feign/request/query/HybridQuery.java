package com.saltlux.searchstudio.api.feign.request.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.Operator;
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
public class HybridQuery extends Query {

    private String query;
    private String field;
    private Double boost;
    private Operator operator;
    private String tokenizer;

    @Builder(builderMethodName = "HybridQueryBuilder")
    private HybridQuery(String query, String field, Double boost, Operator operator, String tokenizer) {
        super(QueryType.HYBRID_QUERY.getText());
        this.query = query;
        this.field = field;
        this.boost = boost;
        this.operator = operator;
        this.tokenizer = tokenizer;
    }
}
