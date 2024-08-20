package com.saltlux.searchstudio.api.service.impl;

import static com.saltlux.searchstudio.api.util.CommonUtil.convertMap;
import static org.assertj.core.api.Assertions.assertThat;

import com.saltlux.searchstudio.api.ApiConstants.Index;
import com.saltlux.searchstudio.api.IntegrationTestSupport;
import com.saltlux.searchstudio.api.controller.request.SubjectRequest;
import com.saltlux.searchstudio.api.feign.SearchStudioFeign;
import com.saltlux.searchstudio.api.feign.request.StandardSearchRequest;
import com.saltlux.searchstudio.api.feign.request.TopicRankRequest;
import com.saltlux.searchstudio.api.feign.request.common.Collapse;
import com.saltlux.searchstudio.api.feign.request.common.PublishedAt;
import com.saltlux.searchstudio.api.feign.request.common.Sort;
import com.saltlux.searchstudio.api.feign.request.enums.DataType;
import com.saltlux.searchstudio.api.feign.request.enums.Field;
import com.saltlux.searchstudio.api.feign.request.enums.Occur;
import com.saltlux.searchstudio.api.feign.request.enums.Operator;
import com.saltlux.searchstudio.api.feign.request.enums.Order;
import com.saltlux.searchstudio.api.feign.request.enums.SortType;
import com.saltlux.searchstudio.api.feign.request.enums.Tokenizer;
import com.saltlux.searchstudio.api.feign.request.filter.BoolFilter;
import com.saltlux.searchstudio.api.feign.request.filter.RangeFilter;
import com.saltlux.searchstudio.api.feign.request.filter.TermFilter;
import com.saltlux.searchstudio.api.feign.request.filter.TermsFilter;
import com.saltlux.searchstudio.api.feign.request.query.BoolQuery;
import com.saltlux.searchstudio.api.feign.request.query.HybridQuery;
import com.saltlux.searchstudio.api.feign.request.query.Query;
import com.saltlux.searchstudio.api.feign.response.StandardSearchResponse;
import com.saltlux.searchstudio.api.feign.response.SynonymResponse;
import com.saltlux.searchstudio.api.feign.response.TopicRankResponse;
import com.saltlux.searchstudio.api.util.CommonUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class SearchServiceImplTest extends IntegrationTestSupport {

    @Autowired
    SearchStudioFeign searchStudioFeign;

    @Test
    @DisplayName("키워드 검색 테스트")
    void standardSearchTest() {
        // given
//        SubjectRequest request = SubjectRequest.builder()
//            .keyword("선박")
//            .startDate("2020")
//            .endDate("2024")
//            .credits("3.0")
//            .build();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        BoolQuery.BoolQueryBuilder boolQueryBuilder = BoolQuery.BoolQueryBuilder();

//        List<BoolQuery.Clauses> queryClauses = new ArrayList<>();

        // 검색어
//        queryClauses.add(createBoolQueryClause(
//            HybridQuery.HybridQueryBuilder()
//                .query(request.getKeyword())
//                .field(Field._SEARCH.getText())
//                .operator(Operator.AND)
//                .tokenizer(Tokenizer.KOR_BIGRAM.getText())
//                .build(),
//            Occur.MUST));

//        queryClauses.add(createBoolQueryClause(
//            SyntaxQuery.SyntaxQueryBuilder()
//                .query(request.getKeyword())
//                .field("subject_morph")
//                .operator(Operator.AND)
//                .tokenizer(Tokenizer.KOREAN.getText())
//                .boost(10.0)
//                .build(),
//            Occur.SHOULD));

//        boolQueryBuilder.clauses(queryClauses);

//        BoolQuery wholeQuery = boolQueryBuilder.build();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        BoolFilter.BoolFilterBuilder boolFilterBuilder = BoolFilter.BoolFilterBuilder();

        List<BoolFilter.Clauses> filterClauses = new ArrayList<>();

//        filterClauses.add(BoolFilter.Clauses.builder()
//            .filter(
//                RangeFilter.RangeFilterBuilder()
//                    .field("YEAR")
//                    .type(DataType.INTEGER)
//                    .lower("2019")
//                    .upper("2024")
//                    .lower_inclusive(true)
//                    .upper_inclusive(true)
//                    .build()
//            ).occur(Occur.MUST)
//            .build());

//        filterClauses.add(BoolFilter.Clauses.builder()
//            .filter(
//                RangeFilter.RangeFilterBuilder()
//                    .field("CDT_NUM")
//                    .type(DataType.DOUBLE)
//                    .lower("2.0")
//                    .upper("2.0")
//                    .lower_inclusive(true)
//                    .upper_inclusive(true)
//                    .build()
//            ).occur(Occur.MUST)
//            .build());

//        filterClauses.add(BoolFilter.Clauses.builder()
//            .filter(
//                TermsFilter.TermsFilterBuilder()
//                    .field("COLG_CD")
//                    .terms(List.of("201000"))
//                    .build()
//            )
//            .occur(Occur.MUST)
//            .build());

        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(
                TermFilter.TermFilterBuilder()
                    .field("COLG_CD")
                    .term("201000")
                    .build()
            )
            .occur(Occur.MUST)
            .build());

        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(
                TermFilter.TermFilterBuilder()
                    .field("DEPT_CD")
                    .term("201550")
                    .build()
            )
            .occur(Occur.MUST)
            .build());

        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(
                TermFilter.TermFilterBuilder()
                    .field("COMDIV_CD")
                    .term("UE010023")
                    .build()
            )
            .occur(Occur.MUST)
            .build());


        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(
                TermFilter.TermFilterBuilder()
                    .field("MAJOR_GB_CD")
                    .term("UE360003")
                    .build()
            )
            .occur(Occur.MUST)
            .build());


        boolFilterBuilder.clauses(filterClauses);

        BoolFilter wholeFilter = boolFilterBuilder.build();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        StandardSearchRequest standardSearchRequest = StandardSearchRequest.builder()
            .indexes(List.of(Index.LECTURE))
//            .query(wholeQuery)
            .filter(wholeFilter)
            .sort(
                List.of(
                    Sort.builder()
                        .sortType(SortType.SCORE_SORT.getText())
                        .order(Order.DESC)
                        .build(),
                    Sort.builder()
                        .sortType(SortType.FIELD_SORT.getText())
                        .field("SUBJECT_NM")
                        .order(Order.DESC)
                        .build()
                )
            )
            .fields(
                List.of(
                    "ID",
                    "YEAR",
                    "SMT_CD",
                    "SMT_NM",
                    "SUBJECT_CD",
                    "SUBJECT_NM",
                    "SUBJECT_ENM",
                    "DEPT_CD",
                    "DEPT_NM",
                    "COLG_CD",
                    "COLG_NM",
                    "GRADE",
                    "SINBUN_CD",
                    "SINBUN_NM",
                    "COMDIV_CD",
                    "COMDIV_NM",
                    "DEG_GB_CD",
                    "DEG_GB_NM",
                    "MNRCOM_DIV_CD",
                    "MNRCOM_DIV_NM",
                    "CDT_NUM",
                    "WTIME_NUM",
                    "PTIME_NUM",
                    "SISU",
                    "COM_CNT",
                    "FUSE_DEPT_CD",
                    "FUSE_DEPT_NM",
                    "FUSE_DOMAIN_CD",
                    "FUSE_DOMAIN_NM",
                    "MAJOR_GB_CD",
                    "MAJOR_GB_NM",
                    "SUBJ_DESC_KOR",
                    "SUBJ_DESC_ENG"
                )
            )
            .collapse(Collapse.builder()
                .field("SUBJECT_CD")
                .build()
            )
            .returnFrom(0)
            .returnSize(20)
            .build();

        CommonUtil.printRequestLog(standardSearchRequest.getClass().getName(), standardSearchRequest);

        // when
        StandardSearchResponse response = searchStudioFeign.standardSearch(standardSearchRequest);

        List<Map<String, Object>> result = response.getResult().getDocuments().stream()
            .map(document -> {
                Map<String, List<String>> fields = document.getFields();
                Map<String, Object> map = convertMap(fields);

                // 문서의 ID를 추가
                map.put("id", document.getId());
                map.put("score", document.getScore());

                return map;
            })
            .toList();

        for (Map<String, Object> stringObjectMap : result) {
            System.out.println(stringObjectMap.toString());
        }

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(200);
//        assertThat(response.getResult().getTotal_hits()).isEqualTo(4);
    }

    @Test
    @DisplayName("토픽랭크 테스트")
    void topicRankTest() {
        // given
        String keyword = "서울";

        TopicRankRequest request = TopicRankRequest.builder()
            .indexes(List.of("news"))
            .published_at(
                PublishedAt.builder()
                    .from("2020")
                    .to("2024")
                    .build()
            )
            .query(
                HybridQuery.HybridQueryBuilder()
                    .query(keyword)
                    .field(Field._SEARCH.getText())
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOR_BIGRAM.getText())
                    .build()
            )
            .filter(
                TermsFilter.TermsFilterBuilder()
                    .terms(List.of("KBS", "조선일보"))
                    .field("category")
                    .build()
            )
            .topic(keyword)
            .max_document_count(300)
            .level(2)
            .top_n_clusters(10)
            .build();

        // when
        TopicRankResponse response = searchStudioFeign.topicRank(request);

        // then
        assertThat(response.getCode()).isEqualTo(200);
        assertThat(response.getResult()).isNotNull();
        assertThat(response.getResult().getNodes().size()).isNotZero();

        List<String> words = response.getResult().getNodes().stream()
            .map(node -> replaceUnderscoreWithSpace(node.getName()))
            .toList();

        for (String word : words) {
            System.out.println("word = " + word);
        }
    }

    public static String replaceUnderscoreWithSpace(String keyword) {
        return keyword.replace("_", " ");
    }

    public static BoolQuery.Clauses createBoolQueryClause(Query query, Occur occur) {
        return BoolQuery.Clauses.builder()
            .query(query)
            .occur(occur)
            .build();
    }

    @Test
    @DisplayName("유의어 사전 조회 테스트")
    void synonymTest() {
        // given
        String keyword = "카카오톡";

        // when
        SynonymResponse response = searchStudioFeign.synonym(keyword);

        // then
        assertThat(response.getCode()).isEqualTo(200);
        assertThat(response.getResult().size()).isNotZero();
        assertThat(response.getResult()).containsOnly("카톡", "카카오톡");
    }

    @Test
    @DisplayName("_ml_feature 값 상위 N개 가져오기")
    void topN_ml_feature_test() {
        // given
        String input = "한국투자증권|0.0498463733930748 1분기_영업적자|0.04405774752116696 카카오페이|0.04031048055040439 오페이|0.04025247818993832 리테일|0.03924992896941905 리테일_매출_증가|0.03899469875051456 리테일_매출|0.035725128748703025 1분기_영업비용|0.03418054882992622 영업적자_89억|0.033924979663174766 3일_카카오페이|0.03229971191652079 정호윤|0.032206639703691954 투자의견|0.03189983899761481 하회|0.031782047220488004 연구원|0.031477017212986506 130억|0.031465860854596864 카카오페이증권|0.03126853621334675 카카|0.03126300871287878 컨센서스|0.031157167949449362 1545억|0.03100606230409163 정호윤_연구원|0.0292447642938744 파이낸셜뉴스|0.022617090991435544 영업|5.585987760473428e-4 증가|4.7370925194089767e-4 비용|4.708540562879064e-4 적자|3.997669104923939e-4 매출|3.8541654170432915e-4 중립|3.3488303892129084e-4 의견|3.283470075227425e-4 투자|3.248357224003901e-4 평가|3.227311982186936e-4 설명|3.066907154982169e-4 유지|2.3630479884875874e-4";
        int topN = 10;

        // when
        Map<String, Double> topNTokens = getTopNTokens(input, topN);

        // then
        for (Map.Entry<String, Double> entry : topNTokens.entrySet()) {
            System.out.println("텍스트: " + entry.getKey() + ", 값: " + entry.getValue());
        }
    }

    private static Map<String, Double> getTopNTokens(String input, int topN) {
        // 문자열을 공백으로 분할하여 배열로 변환
        String[] tokens = input.split("\\s+");

        // 토큰을 숫자 값을 기준으로 내림차순으로 정렬
        return Arrays.stream(tokens)
            .map(token -> token.split("\\|"))
            .collect(
                Collectors.toMap(parts -> replaceUnderscoreWithSpace(parts[0]), parts -> Double.parseDouble(parts[1])))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(topN)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Test
    @DisplayName("_ml_raw_stream_index 값 상위 N개 가져오기")
    void topN_ml_raw_stream_index_test() {
        // given
        String input = "리테일|9.38586990760738 매출|9.226937210460829 증가|9.208206710547168 영업|7.717761628749824 비용|7.47179350403149 정호윤|5.986972652844066 적자|5.760269510976564 연구원|5.748995418948363 영업적자|5.172854104573734 1분기|4.727858097835584 오페이|4.375561153964478 카카|4.278863414377641 카카오페이증권|4.215161291516805 한국투자증권|4.0755369592423305 카카오페이|3.9975298615711803 1545억|3.9805696806727604 리테일_매출|3.7987789380691983 평가|3.7816134690620773 하회|3.6752832830379574 컨센서스|3.607789264773588 130억|3.5897613941623785 1분기_영업비용|3.526867322401839 영업비용|3.526867322401839 리테일_매출_증가|3.524526245026538 89억|3.444044546842606 영업적자_89억|3.444044546842606 정호윤_연구원|3.272115875432446 1분기_영업적자|3.1486265392141366 설명|2.755695579546964 투자의견|2.6162826649066884 파이낸셜뉴스|2.5662076998359655 3일|2.525532652236128 3일_카카오페이|2.525532652236128 투자|2.47746406664248 의견|2.358066566557244 중립|2.232292020127618 유지|2.098994376823296";
        int topN = 10;

        // when
        Map<String, Double> topNTokens = getTopNTokens(input, topN);

        // then
        for (Map.Entry<String, Double> entry : topNTokens.entrySet()) {
            System.out.println("텍스트: " + entry.getKey() + ", 값: " + entry.getValue());
        }
    }
}