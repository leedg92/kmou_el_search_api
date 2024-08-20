package com.saltlux.searchstudio.api.feign.request.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.QueryType;
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
public class TermsQuery extends Query {

    private List<String> terms;
    private String field;
    private Double boost;

    @Builder(builderMethodName = "TermsQueryBuilder")
    private TermsQuery(List<String> terms, String field, Double boost) {
        super(QueryType.TERMS_QUERY.getText());
        this.terms = terms;
        this.field = field;
        this.boost = boost;
    }
}
