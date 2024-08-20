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
public class VectorQuery extends Query {

    private String query;
    private List<Double> vector;
    private List<String> field;
    private Integer k;
    private Integer numCandidates;
    private Boolean fullScan;

    @Builder(builderMethodName = "VectorQueryBuilder")
    private VectorQuery(String query, List<Double> vector, List<String> field, Integer k, Integer numCandidates, Boolean fullScan) {
        super(QueryType.VECTOR_QUERY.getText());
        this.query = query;
        this.vector = vector;
        this.field = field;
        this.k = k;
        this.numCandidates = numCandidates;
        this.fullScan = fullScan;
    }
}
