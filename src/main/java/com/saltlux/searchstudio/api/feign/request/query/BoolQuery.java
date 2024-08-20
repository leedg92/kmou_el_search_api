package com.saltlux.searchstudio.api.feign.request.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.enums.Occur;
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
public class BoolQuery extends Query {

    private List<Clauses> clauses;

    @Builder(builderMethodName = "BoolQueryBuilder")
    private BoolQuery(List<Clauses> clauses) {
        super(QueryType.BOOL_QUERY.getText());
        this.clauses = clauses;
    }

    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Clauses {

        private Query query;
        private Occur occur;

        @Builder
        private Clauses(Query query, Occur occur) {
            this.query = query;
            this.occur = occur;
        }
    }
}
