package com.saltlux.searchstudio.api.feign.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.common.PublishedAt;
import com.saltlux.searchstudio.api.feign.request.filter.Filter;
import com.saltlux.searchstudio.api.feign.request.query.Query;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentCountRequest {

    private List<String> indexes;

    private List<String> routes;

    private PublishedAt published_at;

    private Query query;

    private Filter filter;
   

    @Builder
    private DocumentCountRequest(List<String> indexes, List<String> routes, PublishedAt published_at, 
    		Query query, Filter filter) {
        this.indexes = indexes;
        this.routes = routes;
        this.published_at = published_at;
        this.query = query;
        this.filter = filter;
    }
}
