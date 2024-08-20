package com.saltlux.searchstudio.api.feign.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TopicRankResponse {

    private int code;
    private String message;
    private Result result;

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

        private int total_hits;
        private List<Node> nodes;
        private List<Edge> edges;

        @Getter
        @ToString
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Node {

            private int id;
            private String name;
            private double weight;
        }

        @Getter
        @ToString
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Edge {

            private int from;
            private int to;
        }
    }
}
