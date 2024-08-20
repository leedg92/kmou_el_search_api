package com.saltlux.searchstudio.api.feign.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
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
public class StandardSearchResponse {

    private int code;
    private String message;
    private Result result;

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

        private int total_hits;
        private String total_hits_about;
        private List<Document> documents;

        @Getter
        @ToString
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Document {

            private Map<String, List<String>> fields;
            private Highlights highlights;
            private double score;
            private String index;
            private String id;

            @Getter
            @ToString
            @NoArgsConstructor(access = AccessLevel.PROTECTED)
            @AllArgsConstructor
            public static class Highlights {

                private String summary;
            }
        }
    }
}
