package com.saltlux.searchstudio.api.feign.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saltlux.searchstudio.api.feign.request.common.Collapse;
import com.saltlux.searchstudio.api.feign.request.common.Highlights;
import com.saltlux.searchstudio.api.feign.request.common.PublishedAt;
import com.saltlux.searchstudio.api.feign.request.common.Sort;
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
public class StandardSearchRequest {

    private List<String> indexes;

    private List<String> routes;

    private PublishedAt published_at;

    private Query query;

    private Filter filter;

    private List<Sort> sort;

    private Highlights highlights;

    private List<String> fields;

    private Collapse collapse;

    private Integer track_total_hits;

    private Integer returnFrom;

    private Integer returnSize;

    @Builder
    private StandardSearchRequest(List<String> indexes, List<String> routes, PublishedAt published_at, Query query, Filter filter,
        List<Sort> sort, Highlights highlights, List<String> fields, Collapse collapse, Integer track_total_hits, Integer returnFrom,
        Integer returnSize) {
        this.indexes = indexes;
        this.routes = routes;
        this.published_at = published_at;
        this.query = query;
        this.filter = filter;
        this.sort = sort;
        this.highlights = highlights;
        this.fields = fields;
        this.collapse = collapse; //중복제거
        this.track_total_hits = track_total_hits;
        this.returnFrom = returnFrom;
        this.returnSize = returnSize;
    }

}
