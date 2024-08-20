package com.saltlux.searchstudio.api.util;

import com.saltlux.searchstudio.api.ApiConstants;
import com.saltlux.searchstudio.api.feign.request.enums.Field;
import com.saltlux.searchstudio.api.feign.request.enums.Occur;
import com.saltlux.searchstudio.api.feign.request.enums.Operator;
import com.saltlux.searchstudio.api.feign.request.enums.Tokenizer;
import com.saltlux.searchstudio.api.feign.request.query.BoolQuery;
import com.saltlux.searchstudio.api.feign.request.query.HybridQuery;
import com.saltlux.searchstudio.api.feign.request.query.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class QueryUtil {

    public static Query createQuery(Map<String, Object> request) {
        BoolQuery.BoolQueryBuilder boolQueryBuilder = BoolQuery.BoolQueryBuilder();

        List<BoolQuery.Clauses> queryClauses = new ArrayList<>();

        // 검색어(keyword)
        if (StringUtils.isNotBlank(request.get(ApiConstants.PARAMETER_KEYWORD).toString())) {
            queryClauses.add(createBoolQueryClause(
                HybridQuery.HybridQueryBuilder()
                    .query(request.get(ApiConstants.PARAMETER_KEYWORD).toString())
                    .field(Field._SEARCH.getText())
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOR_BIGRAM.getText())
                    .build(),
                Occur.MUST));
        }

        boolQueryBuilder.clauses(queryClauses);

        return boolQueryBuilder.build();
    }

    public static BoolQuery.Clauses createBoolQueryClause(Query query, Occur occur) {
        return BoolQuery.Clauses.builder()
            .query(query)
            .occur(occur)
            .build();
    }

}
