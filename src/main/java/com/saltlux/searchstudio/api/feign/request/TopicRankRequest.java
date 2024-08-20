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
public class TopicRankRequest {

    private List<String> indexes;

    private List<String> routes;

    private PublishedAt published_at;

    private Query query;

    private Filter filter;

    private String topic;

    private Integer max_document_count;

    private Integer level;

    private Integer top_n_clusters;

    private List<String> stopwords;

    @Builder
    private TopicRankRequest(List<String> indexes, List<String> routes, PublishedAt published_at, Query query, Filter filter,
        String topic, Integer max_document_count, Integer level, Integer top_n_clusters, List<String> stopwords) {
        this.indexes = indexes;
        this.routes = routes;
        this.published_at = published_at;
        this.query = query;
        this.filter = filter;
        this.topic = topic;
        this.max_document_count = max_document_count;
        this.level = level;
        this.top_n_clusters = top_n_clusters;
        this.stopwords = stopwords;
    }
}
