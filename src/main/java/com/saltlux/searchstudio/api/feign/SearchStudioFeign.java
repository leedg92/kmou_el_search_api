package com.saltlux.searchstudio.api.feign;

import com.saltlux.searchstudio.api.feign.request.DocumentCountRequest;
import com.saltlux.searchstudio.api.feign.request.StandardSearchRequest;
import com.saltlux.searchstudio.api.feign.request.TopicRankRequest;
import com.saltlux.searchstudio.api.feign.response.DocumentCountResponse;
import com.saltlux.searchstudio.api.feign.response.StandardSearchResponse;
import com.saltlux.searchstudio.api.feign.response.SynonymResponse;
import com.saltlux.searchstudio.api.feign.response.TopicRankResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Search-Studio-API", url = "${search-studio.url}")
public interface SearchStudioFeign {

    /**
     * 키워드 검색
     */
    @PostMapping(value = "/api/v1/search/standard")
    StandardSearchResponse standardSearch(StandardSearchRequest request);

    /**
     * 문서 수 검색
     */
    @PostMapping(value = "/api/v1/search/document/count")
    DocumentCountResponse documentCountsearch(DocumentCountRequest request);

    /**
     * 토픽랭크
     */
    @PostMapping(value = "/api/v1/keyword/topicRank")
    TopicRankResponse topicRank(TopicRankRequest request);

    /**
     * 유의어
     */
    @GetMapping(value = "/api/v1/decorator/dictionary/synonym")
    SynonymResponse synonym(@RequestParam("keyword") String keyword);

}
