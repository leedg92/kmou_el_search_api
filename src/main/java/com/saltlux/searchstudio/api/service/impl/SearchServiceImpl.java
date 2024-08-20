package com.saltlux.searchstudio.api.service.impl;

import static com.saltlux.searchstudio.api.util.CommonUtil.convertMap;
import static com.saltlux.searchstudio.api.util.CommonUtil.printRequestLog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saltlux.searchstudio.api.ApiConstants.Field;
import com.saltlux.searchstudio.api.ApiConstants.Index;
import com.saltlux.searchstudio.api.controller.request.MajorRequest;
import com.saltlux.searchstudio.api.controller.request.NonSubjectRequest;
import com.saltlux.searchstudio.api.controller.request.ProfessorRequest;
import com.saltlux.searchstudio.api.controller.request.RelatedKeywordsRequest;
import com.saltlux.searchstudio.api.controller.request.SubjectRequest;
import com.saltlux.searchstudio.api.feign.SearchStudioFeign;
import com.saltlux.searchstudio.api.feign.request.DocumentCountRequest;
import com.saltlux.searchstudio.api.feign.request.StandardSearchRequest;
import com.saltlux.searchstudio.api.feign.request.TopicRankRequest;
import com.saltlux.searchstudio.api.feign.request.common.Collapse;
import com.saltlux.searchstudio.api.feign.request.enums.DataType;
import com.saltlux.searchstudio.api.feign.request.enums.Occur;
import com.saltlux.searchstudio.api.feign.request.enums.Operator;
import com.saltlux.searchstudio.api.feign.request.enums.Tokenizer;
import com.saltlux.searchstudio.api.feign.request.filter.BoolFilter;
import com.saltlux.searchstudio.api.feign.request.filter.Filter;
import com.saltlux.searchstudio.api.feign.request.filter.RangeFilter;
import com.saltlux.searchstudio.api.feign.request.filter.TermFilter;
import com.saltlux.searchstudio.api.feign.request.filter.TermsFilter;
import com.saltlux.searchstudio.api.feign.request.query.BoolQuery;
import com.saltlux.searchstudio.api.feign.request.query.HybridQuery;
import com.saltlux.searchstudio.api.feign.request.query.Query;
import com.saltlux.searchstudio.api.feign.request.query.SyntaxQuery;
import com.saltlux.searchstudio.api.feign.request.query.TermsQuery;
import com.saltlux.searchstudio.api.feign.response.StandardSearchResponse;
import com.saltlux.searchstudio.api.feign.response.TopicRankResponse;
import com.saltlux.searchstudio.api.service.SearchService;
import com.saltlux.searchstudio.api.service.response.BaseResponse;
import com.saltlux.searchstudio.api.util.ExternalApiUtil;
import com.saltlux.searchstudio.api.util.FilterUtil;
import com.saltlux.searchstudio.api.util.QueryUtil;
import com.saltlux.searchstudio.api.util.RequestParamUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchStudioFeign searchStudioFeign;

    /**
     * <pre>
     * 교욱과정 검색
     * 1. 먼저 개설과목(lecture) 검색 수행(query, filter)
     * 2. 개설과목 검색 결과로 중복제거 된 LECTURE_ID 추출
     * 3. 교육과정(curriculum)에 LECTURE_ID TERMS 검색 수행(정렬 조건 및 페이징 처리)
     * </pre>
     */
    @Override
    public BaseResponse subject(SubjectRequest request) {
        //////////////////////////////////////////////////////////////////////
        // 검색 조건
        BoolQuery.BoolQueryBuilder boolQueryBuilder = BoolQuery.BoolQueryBuilder();
        List<BoolQuery.Clauses> queryClauses = new ArrayList<>();

        // 검색어(keyword)
        if (StringUtils.isNotBlank(request.getKeyword())) {
            queryClauses.add(BoolQuery.Clauses.builder().
                query(HybridQuery.HybridQueryBuilder()
                    .query(request.getKeyword())
                    .field(Field._SEARCH)
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOR_BIGRAM.getText())
                    .build()).occur(Occur.MUST)
                .build()
            );

//            queryClauses.add(BoolQuery.Clauses.builder().
//                query(SyntaxQuery.SyntaxQueryBuilder()
//                    .query(request.getKeyword())
//                    .field(Field._SBJT_MORPH)
//                    .operator(Operator.AND)
//                    .tokenizer(Tokenizer.KOREAN.getText())
//                    .boost(2)
//                    .build()).occur(Occur.SHOULD)
//                .build()
//            );
//
//            queryClauses.add(BoolQuery.Clauses.builder().
//                query(SyntaxQuery.SyntaxQueryBuilder()
//                    .query(request.getKeyword())
//                    .field(Field._SBJT_BIGRAM)
//                    .operator(Operator.AND)
//                    .tokenizer(Tokenizer.KOREAN.getText())
//                    .boost(1)
//                    .build()).occur(Occur.SHOULD)
//                .build()
//            );
        }

        boolQueryBuilder.clauses(queryClauses);
        Query query = boolQueryBuilder.build();
        //////////////////////////////////////////////////////////////////////
        // 필터 조건
        BoolFilter.BoolFilterBuilder boolFilterBuilder = BoolFilter.BoolFilterBuilder();
        List<BoolFilter.Clauses> filterClauses = new ArrayList<>();

        // 폐강 아닌 데이터만 조회
        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(TermFilter.TermFilterBuilder()
                .term("N")
                .field(Field.ABO_YN)
                .build())
            .occur(Occur.MUST)
            .build());

        // 학부만 조회
        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(TermFilter.TermFilterBuilder()
                .term("UD340001")
                .field(Field.SINBUN_CD)
                .build())
            .occur(Occur.MUST)
            .build());

        // 개설연도(시작)
        if (request.getStartDate() != null && StringUtils.isNotBlank(request.getStartDate())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.YEAR)
                    .type(DataType.INTEGER)
                    .lower(request.getStartDate())
                    .lower_inclusive(true)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 개설연도(종료)
        if (request.getEndDate() != null && StringUtils.isNotBlank(request.getEndDate())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.YEAR)
                    .type(DataType.INTEGER)
                    .upper(request.getEndDate())
                    .upper_inclusive(true)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 대학
        if (request.getUniversity() != null && StringUtils.isNotBlank(request.getUniversity())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getUniversity())
                    .field(Field.COLG_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 학부/학과
        if (request.getDepartment() != null && StringUtils.isNotBlank(request.getDepartment())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getDepartment())
                    .field(Field.DEPT_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 전공
        if (request.getMajor() != null && StringUtils.isNotBlank(request.getMajor())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getMajor())
                    .field(Field.MAJOR_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 년도
        if (request.getYear() != null && StringUtils.isNotBlank(request.getYear())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.YEAR)
                    .type(DataType.INTEGER)
                    .lower(request.getYear())
                    .upper(request.getYear())
                    .lower_inclusive(true)
                    .upper_inclusive(true)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 학년
        if (request.getGrade() != null && StringUtils.isNotBlank(request.getGrade())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.GRADE)
                    .type(DataType.INTEGER)
                    .lower(request.getGrade())
                    .upper(request.getGrade())
                    .lower_inclusive(true)
                    .upper_inclusive(true)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 학기
        if (request.getSemester() != null && StringUtils.isNotBlank(request.getSemester())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getSemester())
                    .field(Field.SMT_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 이수구분
        if (request.getCourseClassification() != null && StringUtils.isNotBlank(request.getCourseClassification())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermsFilter.TermsFilterBuilder()
                    .terms(Arrays.stream(request.getCourseClassification().split("\\s*,\\s*"))
                        .collect(Collectors.toList()))
                    .field(Field.COMDIV_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 핵심역량(주역량)
        if (request.getCoreCompetence() != null && StringUtils.isNotBlank(request.getCoreCompetence())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermsFilter.TermsFilterBuilder()
                    .terms(Arrays.stream(request.getCoreCompetence().split("\\s*,\\s*"))
                        .collect(Collectors.toList()))
                    .field(Field.MAIN_ABI_NM)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 핵심역량(부역량)
        if (request.getSubCoreCompetence() != null && StringUtils.isNotBlank(request.getSubCoreCompetence())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermsFilter.TermsFilterBuilder()
                    .terms(Arrays.stream(request.getSubCoreCompetence().split("\\s*,\\s*"))
                        .collect(Collectors.toList()))
                    .field(Field.SUB_ABI_NM)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        boolFilterBuilder.clauses(filterClauses);
        Filter filter = boolFilterBuilder.build();
        //////////////////////////////////////////////////////////////////////

        // 개설과목(lecture)
        List<String> lectureIndex = Collections.singletonList(Index.LECTURE);

        // 개설과목 총 건수 조회
        int totalHits = getTotalHits(lectureIndex, query, filter);
//        printRequestLog("Total documents", totalHits);

        // 출력 필드
        List<String> returnFields = Arrays.asList(
            Field.LECTURE_ID
            , Field.SUBJECT_CD
            , Field.SUBJECT_NM
            , Field.SUBJECT_ENM
            , Field.YEAR
            , Field.SMT_CD
            , Field.SMT_NM
            , Field.DEPT_CD
            , Field.DEPT_NM
            , Field.COLG_CD
            , Field.COLG_NM
            , Field.MAJOR_CD
            , Field.MAJOR_NM
            , Field.SINBUN_CD
            , Field.SINBUN_NM
            , Field.DEG_GB_CD
            , Field.DEG_GB_NM
            , Field.COMDIV_CD
            , Field.COMDIV_NM
            , Field.MNRCOM_DIV_CD
            , Field.MNRCOM_DIV_NM
            , Field.CLASS_CD
            , Field.CLASS_NM
            , Field.GRADE
            , Field.CDT_NUM
            , Field.WTIME_NUM
            , Field.PTIME_NUM
            , Field.SISU
            , Field.SUBJ_DESC_KOR
            , Field.SUBJ_DESC_ENG
            //테스트
//                , Field.MAJOR_ABI
//                , Field.ESSENTIAL_ABI
//                , Field.MAIN_ABI_NM
//                , Field.SUB_ABI_NM
        );

        // 개설과목 조회
        StandardSearchRequest lectureRequest = StandardSearchRequest.builder()
            .indexes(lectureIndex)
            .query(query)
            .filter(filter)
            .fields(returnFields)
            .collapse(Collapse.builder()    // 중복제거
                .field(Field.COLLAPSE_ID)
                .build())
            .sort(request.getSort())
            .returnFrom(0)
            .returnSize(totalHits)
            .build();
//        printRequestLog("lecture search", lectureRequest);

        StandardSearchResponse lectureResponse = searchStudioFeign.standardSearch(lectureRequest);
//        printRequestLog("lecture size", lectureResponse.getResult().getTotal_hits());


        // LECTURE_ID 추출
        List<String> lectureIdList = new LinkedList<>();
        lectureResponse.getResult().getDocuments().forEach(document -> {
                Map<String, List<String>> fields = document.getFields();

                if (!fields.get(Field.LECTURE_ID).isEmpty()) {
                    lectureIdList.add(fields.get(Field.LECTURE_ID).get(0));
                }
            }
        );
//        printRequestLog("lectureIdList size", lectureIdList.size());

        // 재검색
        lectureRequest = StandardSearchRequest.builder()
            .indexes(lectureIndex)
            .query(query)
            .filter(filter)
            .fields(returnFields)
            .collapse(Collapse.builder()    // 중복제거
                .field(Field.COLLAPSE_ID)
                .build())
            .sort(request.getSort())
            .returnFrom(request.getPageNum())
            .returnSize(request.getPagePer())
            .build();

        lectureResponse = searchStudioFeign.standardSearch(lectureRequest);
//        printRequestLog("lecture size", lectureResponse.getResult().getTotal_hits());

        return BaseResponse.builder()
            .result(getDocuments(lectureResponse))
            .totalCount(lectureIdList.size())
            .build();

//        Map<String, String> latestByGroup = new HashMap<>();
//
//        for (String lectureId : lectureIdList) {
//            String[] parts = lectureId.split("_");
//            String year = parts[0];
//            String groupKey = parts[1] + "_" + parts[2] + "_" + parts[3] + "_" + parts[4];
//
//            if (!latestByGroup.containsKey(groupKey) || year.compareTo(latestByGroup.get(groupKey).split("_")[0]) > 0) {
//                latestByGroup.put(groupKey, lectureId);
//            }
//        }
//
//        List<String> latestLectureIds = new ArrayList<>(latestByGroup.values());
//
//        System.out.println("<추출된 최신 코드 리스트>");
//        if(latestLectureIds.size() > 0) {
//        	lectureIdList.clear();
//        }
//        for (String lectureId : latestLectureIds) {
//        	System.out.println(lectureId);
//        	lectureIdList.add(lectureId);
//        }
//
//        List<String> returnFields = Arrays.asList(
//        	Field.LECTURE_ID
//            , Field.SUBJECT_CD
//            , Field.SUBJECT_NM
//            , Field.SUBJECT_ENM
//            , Field.YEAR
//            , Field.SMT_CD
//            , Field.SMT_NM
//            , Field.DEPT_CD
//            , Field.DEPT_NM
//            , Field.COLG_CD
//            , Field.COLG_NM
//            , Field.MAJOR_CD
//            , Field.MAJOR_NM
//            , Field.SINBUN_CD
//            , Field.SINBUN_NM
//            , Field.DEG_GB_CD
//            , Field.DEG_GB_NM
//            , Field.COMDIV_CD
//            , Field.COMDIV_NM
//            , Field.MNRCOM_DIV_CD
//            , Field.MNRCOM_DIV_NM
//            , Field.CLASS_CD
//            , Field.CLASS_NM
//            , Field.GRADE
//            , Field.CDT_NUM
//            , Field.WTIME_NUM
//            , Field.PTIME_NUM
//            , Field.SISU
//            , Field.SUBJ_DESC_KOR
//            , Field.SUBJ_DESC_ENG
//        );

        //////////////////////////////////////////////////////////////////////
        // 교육과정(curriculum) 조회
        //////////////////////////////////////////////////////////////////////
//        if (request.getSort() == null) { // 정확도순
//            List<String> subList = lectureIdList.subList(request.getPageNum() * request.getPagePer(),
//                Math.min(lectureIdList.size(), request.getPageNum() * request.getPagePer() + request.getPagePer()));
//
//            StandardSearchRequest curriculumRequest = StandardSearchRequest.builder()
//                .indexes(Collections.singletonList(Index.CURRICULUM))
//                .query(TermsQuery.TermsQueryBuilder()
//                    .terms(subList)
//                    .field(Field.LECTURE_ID)
//                    .build())
//                .collapse(Collapse.builder()    // 중복제거
//                    .field(Field.LECTURE_ID)
//                    .build())
//                .fields(returnFields)
//                .returnFrom(0)
//                .returnSize(subList.size())
//                .build();
//
//            printRequestLog("curriculum search", curriculumRequest);
//
//            StandardSearchResponse curriculumResponse = searchStudioFeign.standardSearch(curriculumRequest);
//
//            List<Map<String, Object>> sortedResult = sortById(lectureIdList, getDocuments(curriculumResponse));
//
//            return BaseResponse.builder()
//                .result(sortedResult)
//                .totalCount(lectureIdList.size())
//                .build();
//        } else {
//        	totalHits = getTotalHits(Collections.singletonList(Index.CURRICULUM),
//        							TermsQuery.TermsQueryBuilder().terms(lectureIdList).field(Field.LECTURE_ID).build(),
//        							null);
//            printRequestLog("getTotalHits", totalHits);
//
//            StandardSearchRequest curriculumRequest = StandardSearchRequest.builder()
//                .indexes(Collections.singletonList(Index.CURRICULUM))
//                .query(TermsQuery.TermsQueryBuilder()
//                    .terms(lectureIdList)
//                    .field(Field.LECTURE_ID)
//                    .build())
//                .collapse(Collapse.builder()    // 중복제거
//                    .field(Field.LECTURE_ID)
//                    .build())
//                .sort(request.getSort())
//                .fields(returnFields)
//                .returnFrom(request.getPageNum())
//                .returnSize(request.getPagePer())
//                .build();
//
//            printRequestLog("curriculum search", curriculumRequest);
//
//            StandardSearchResponse curriculumResponse = searchStudioFeign.standardSearch(curriculumRequest);
//
////            List<Map<String, Object>> documents = getDocuments(curriculumResponse);
//            documents = getDocuments(curriculumResponse);
//
//            return BaseResponse.builder()
//                .result(documents)
//                .totalCount(totalHits)
//                .build();
//        }
    }

    /**
     * 문서 수 검색
     */
    private int getTotalHits(List<String> indexes, Query query, Filter filter) {
        DocumentCountRequest request = DocumentCountRequest.builder()
            .indexes(indexes)
            .query(query)
            .filter(filter)
            .build();

        printRequestLog("lecture document count", request);

        return searchStudioFeign.documentCountsearch(request)
            .getResult()
            .getTotal_hits();
    }

    private List<Map<String, Object>> sortById(List<String> lectureIds, List<Map<String, Object>> target) {
        List<Map<String, Object>> sortedResult = new LinkedList<>();
        for (String lectureId : lectureIds) {
            for (Map<String, Object> item : target) {
                if (item.get(Field.LECTURE_ID).equals(lectureId)) {
                    sortedResult.add(item);
                    break;
                }
            }
        }
        return sortedResult;
    }

    /**
     * <pre>
     * 교수 검색
     * 1. 먼저 개설과목(lecture) 검색 수행(query, filter)
     * 2. 개설과목 검색 결과로 중복제거 된 EMP_NO 추출
     * 3. 교수(professor)에 EMP_NO TERMS 검색 수행(정렬 조건 및 페이징 처리)
     * </pre>
     */
    @Override
    public BaseResponse professor(ProfessorRequest request) {
        //////////////////////////////////////////////////////////////////////
        // 검색 조건
        BoolQuery.BoolQueryBuilder boolQueryBuilder = BoolQuery.BoolQueryBuilder();
        List<BoolQuery.Clauses> queryClauses = new ArrayList<>();

        // 검색어(keyword)
        if (StringUtils.isNotBlank(request.getKeyword())) {
            queryClauses.add(BoolQuery.Clauses.builder().
                query(HybridQuery.HybridQueryBuilder()
                    .query(request.getKeyword())
                    .field(Field._SEARCH)
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOR_BIGRAM.getText())
                    .build()).occur(Occur.MUST)
                .build()
            );

            queryClauses.add(BoolQuery.Clauses.builder().
                query(SyntaxQuery.SyntaxQueryBuilder()
                    .query(request.getKeyword())
                    .field(Field._EMP_MORPH)
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOREAN.getText())
                    .boost(2)
                    .build()).occur(Occur.SHOULD)
                .build()
            );

            queryClauses.add(BoolQuery.Clauses.builder().
                query(SyntaxQuery.SyntaxQueryBuilder()
                    .query(request.getKeyword())
                    .field(Field._EMP_BIGRAM)
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOREAN.getText())
                    .boost(1)
                    .build()).occur(Occur.SHOULD)
                .build()
            );
        }

        boolQueryBuilder.clauses(queryClauses);
        Query query = boolQueryBuilder.build();
        //////////////////////////////////////////////////////////////////////
        // 필터 조건
        BoolFilter.BoolFilterBuilder boolFilterBuilder = BoolFilter.BoolFilterBuilder();
        List<BoolFilter.Clauses> filterClauses = new ArrayList<>();

        // 폐강 아닌 데이터만 조회
        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(TermFilter.TermFilterBuilder()
                .term("N")
                .field(Field.ABO_YN)
                .build())
            .occur(Occur.MUST)
            .build());
        // 개설연도(시작)
        if (request.getStartDate() != null && StringUtils.isNotBlank(request.getStartDate())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.YEAR)
                    .type(DataType.INTEGER)
                    .lower(request.getStartDate())
                    .lower_inclusive(true)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 개설연도(종료)
        if (request.getEndDate() != null && StringUtils.isNotBlank(request.getEndDate())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.YEAR)
                    .type(DataType.INTEGER)
                    .upper(request.getEndDate())
                    .upper_inclusive(true)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        boolFilterBuilder.clauses(filterClauses);
        Filter filter = boolFilterBuilder.build();
        //////////////////////////////////////////////////////////////////////

        // 개설과목(lecture)
        List<String> lectureIndex = Collections.singletonList(Index.LECTURE);

        // 개설과목 총 건수 조회
        int totalHits = getTotalHits(lectureIndex, query, filter);
        // 개설과목 조회
        StandardSearchRequest lectureRequest = StandardSearchRequest.builder()
            .indexes(lectureIndex)
            .query(query)
            .filter(filter)
            .fields(
                Arrays.asList(
                    Field.YEAR
                    , Field.SMT_NM
                    , Field.SUBJECT_CD
                    , Field.SUBJECT_NM
                    , Field.DEPT_NM
                    , Field.COLG_NM
                    , Field.EMP_NO
                    , Field.EMP_NM
                    , Field.GRADE
                )
            )
            .collapse(Collapse.builder()    // 중복제거
                .field(Field.EMP_NO)
                .build())
            .returnFrom(0)
            .returnSize(totalHits)
            .build();

        printRequestLog("lecture search", lectureRequest);

        StandardSearchResponse lectureResponse = searchStudioFeign.standardSearch(lectureRequest);

        // EMP_NO 추출
        List<String> empNoList = new LinkedList<>();
        lectureResponse.getResult().getDocuments().forEach(document -> {
                Map<String, List<String>> fields = document.getFields();

                if (!fields.get(Field.EMP_NO).isEmpty()) {
                    empNoList.add(fields.get(Field.EMP_NO).get(0));
                }
            }
        );

        //교수 필터

        //초기화
        boolFilterBuilder = BoolFilter.BoolFilterBuilder();
        filterClauses = new ArrayList<>();

        // 대학
        if (request.getUniversity() != null && StringUtils.isNotBlank(request.getUniversity())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getUniversity())
                    .field(Field.COLG_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 학과(학부)
        if (request.getDepartment() != null && StringUtils.isNotBlank(request.getDepartment())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getDepartment())
                    .field(Field.DEPT_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 전공
        if (request.getMajor() != null && StringUtils.isNotBlank(request.getMajor())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getMajor())
                    .field(Field.MAJOR_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        boolFilterBuilder.clauses(filterClauses);
        filter = boolFilterBuilder.build();

        List<String> returnFields = Arrays.asList(Field.EMP_NO
            , Field.EMP_NM
            , Field.EMP_ENM
            , Field.EMP_CNM
            , Field.DEPT_CD
            , Field.DEPT_NM
            , Field.COLG_CD
            , Field.COLG_NM
            , Field.FILE_PATH
            , Field.EMAIL
            , Field.TLPHON
            , Field.RSRCH_REALM
            , Field.LABRUM
        );

        //////////////////////////////////////////////////////////////////////
        // 교수(professor) 조회
        //////////////////////////////////////////////////////////////////////
//        if (request.getSort() == null) { // 정확도순
//            List<String> subList = empNoList.subList(request.getPageNum() * request.getPagePer(),
//                Math.min(empNoList.size(), request.getPageNum() * request.getPagePer() + request.getPagePer()));
//
//            StandardSearchRequest professorRequest = StandardSearchRequest.builder()
//                .indexes(Collections.singletonList(Index.PROFESSOR))
//                .query(TermsQuery.TermsQueryBuilder()
//                    .terms(subList)
//                    .field(Field.EMP_NO)
//                    .build())
//                .fields(returnFields)
//                .returnFrom(0)
//                .returnSize(subList.size())
//                .build();
//
//            printRequestLog("professor search", professorRequest);
//
//            StandardSearchResponse professorResponse = searchStudioFeign.standardSearch(professorRequest);
//
//            List<Map<String, Object>> sortedResult = sortByEmpNo(empNoList, getDocuments(professorResponse));
//
//            return BaseResponse.builder()
//                .result(sortedResult)
//                .totalCount(empNoList.size())
//                .build();
//        } else {
        StandardSearchRequest professorRequest = StandardSearchRequest.builder()
            .indexes(Collections.singletonList(Index.PROFESSOR))
            .query(TermsQuery.TermsQueryBuilder()
                .terms(empNoList)
                .field(Field.EMP_NO)
                .build())
            .filter(filter)
            .sort(request.getSort())
            .fields(returnFields)
            .returnFrom(request.getPageNum())
            .returnSize(request.getPagePer())
            .build();

        printRequestLog("professor search", professorRequest);

//        StandardSearchResponse professorResponse = searchStudioFeign.standardSearch(professorRequest);

//        List<Map<String, Object>> documents = getDocuments(professorResponse);

//            return BaseResponse.builder()
//                .result(documents)
//                .totalCount(empNoList.size())
//                .build();
//        }
        return getSearchStudioResponse(professorRequest);
    }

    private List<Map<String, Object>> sortByEmpNo(List<String> empNoList, List<Map<String, Object>> target) {
        List<Map<String, Object>> sortedResult = new LinkedList<>();
        for (String empNo : empNoList) {
            for (Map<String, Object> item : target) {
                if (item.get(Field.EMP_NO).equals(empNo)) {
                    sortedResult.add(item);
                    break;
                }
            }
        }
        return sortedResult;
    }

    /**
     * <pre>
     * 비교과 검색 분기
     * 1. request에 학번(student_no)가 포함되어있는지 확인한다
     * 2. 학번이 포함되어있으면 추천순이 적용된 검색 호출
     *      ㄴ 전체건수 검색 후 추천결과가 포함되어있으면 위로올리는 작업
     * 3. 학번이 초함되어있지않으면 추천순이 적용되지 않은 일반검색 호출
     * </pre>
     *
     */
    @Override
    public BaseResponse nonSubject(NonSubjectRequest request){
        if(StringUtils.isNotBlank(request.getStudentNo()) && request.getStudentNo() != null){
            return nonSubjectWithRecommend(request);
        }else{
            return nonSubjectWithoutRecommend(request);
        }
    }

    /**
     * 비교과 검색(추천순 O)
     */
    private BaseResponse nonSubjectWithRecommend(NonSubjectRequest request) {

        //AI추천
        String recommendationJson = ExternalApiUtil.getAiRecommendApi(
            ExternalApiUtil.AI_RECOMMEND_URL,
            ExternalApiUtil.AI_RECOMMEND_NONCOURSE_ENDPOINT,
            ExternalApiUtil.METHOD_POST,
            request.getStudentNo()
        );

        // 검색조건
        // 검색어(keyword)
        Query query = buildQuery(request);
        // 필터 조건
        Filter filter = buildFilter(request);


        //////////////////////////////////////////////////////////////////////
        // 비교과 전체 검색(non_lecture)
        StandardSearchRequest fullSearchRequest = StandardSearchRequest.builder()
            .indexes(Collections.singletonList(Index.NON_LECTURE))
            .query(query)
            .filter(filter)
            .sort(request.getSort())
            .fields(getNonSubjectReturnFields())
            .returnFrom(0)
            .returnSize(10000) // 전체검색이기때문에 크게 설정
            .build();

        BaseResponse fullResponse = getSearchStudioResponse(fullSearchRequest);
        List<Map<String, Object>> allSearchResults = (List<Map<String, Object>>) fullResponse.getResult();

        // 추천, 검색 병합
        List<Map<String, Object>> mergedResults = mergeResults(recommendationJson, allSearchResults);

        // 페이징 적용
        int totalCount = mergedResults.size();
        int from = request.getPageNum() * request.getPagePer();
        int to = Math.min(from + request.getPagePer(), totalCount);
        List<Map<String, Object>> pagedResults = mergedResults.subList(from, to);

        return BaseResponse.builder()
            .result(pagedResults)
            .totalCount(totalCount)
            .build();
    }

    /**
     * 비교과 검색(추천순 X)
     */
    private BaseResponse nonSubjectWithoutRecommend(NonSubjectRequest request) {


        // 검색조건
        // 검색어(keyword)
        Query query = buildQuery(request);
        // 필터 조건
        Filter filter = buildFilter(request);

        //////////////////////////////////////////////////////////////////////
        // 비교과 검색(non_lecture)
        //////////////////////////////////////////////////////////////////////
        StandardSearchRequest searchRequest = StandardSearchRequest.builder()
            .indexes(Collections.singletonList(Index.NON_LECTURE))
            .query(query)
            .filter(filter)
            .sort(request.getSort())
            .fields(getNonSubjectReturnFields())
            .returnFrom(request.getPageNum())
            .returnSize(request.getPagePer())
            .build();

        return getSearchStudioResponse(searchRequest);
    }

    /**
     * 비교과 query
     */
    private Query buildQuery(NonSubjectRequest request) {
        BoolQuery.BoolQueryBuilder boolQueryBuilder = BoolQuery.BoolQueryBuilder();
        List<BoolQuery.Clauses> queryClauses = new ArrayList<>();

        if (StringUtils.isNotBlank(request.getKeyword())) {
            queryClauses.add(BoolQuery.Clauses.builder()
                .query(HybridQuery.HybridQueryBuilder()
                    .query(request.getKeyword())
                    .field(Field._SEARCH)
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOR_BIGRAM.getText())
                    .build())
                .occur(Occur.MUST)
                .build()
            );
        }

        boolQueryBuilder.clauses(queryClauses);
        return boolQueryBuilder.build();
    }

    /**
     * 비교과 필터
     */
    private Filter buildFilter(NonSubjectRequest request) {
        BoolFilter.BoolFilterBuilder boolFilterBuilder = BoolFilter.BoolFilterBuilder();
        List<BoolFilter.Clauses> filterClauses = new ArrayList<>();

        // 기본 필터 조건 추가
        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(TermFilter.TermFilterBuilder()
                .term("TRUE")
                .field(Field.IS_VISIBLE)
                .build())
            .occur(Occur.MUST)
            .build());

        filterClauses.add(BoolFilter.Clauses.builder()
            .filter(TermFilter.TermFilterBuilder()
                .term("FALSE")
                .field(Field.IS_DELETE)
                .build())
            .occur(Occur.MUST)
            .build());

        // 날짜 관련 필터 추가
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 신청시작일 범위
        if (StringUtils.isNotBlank(request.getSignInStartDate()) && StringUtils.isNotBlank(request.getSignInStartDate1())) {
            LocalDate upperDate = LocalDate.parse(request.getSignInStartDate1(), formatter);
            String nextDayString = upperDate.plusDays(1).format(formatter);
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.SIGNIN_START_DATE)
                    .type(DataType.DATE)
                    .lower(request.getSignInStartDate())
                    .upper(nextDayString)
                    .lower_inclusive(true)
                    .upper_inclusive(false)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 신청종료일 범위
        if (StringUtils.isNotBlank(request.getSignInEndDate()) && StringUtils.isNotBlank(request.getSignInEndDate1())) {
            LocalDate upperDate = LocalDate.parse(request.getSignInEndDate1(), formatter);
            String nextDayString = upperDate.plusDays(1).format(formatter);
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.SIGNIN_END_DATE)
                    .type(DataType.DATE)
                    .lower(request.getSignInEndDate())
                    .upper(nextDayString)
                    .lower_inclusive(true)
                    .upper_inclusive(false)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 교육시작일 범위
        if (StringUtils.isNotBlank(request.getStartDate()) && StringUtils.isNotBlank(request.getStartDate1())) {
            LocalDate upperDate = LocalDate.parse(request.getStartDate1(), formatter);
            String nextDayString = upperDate.plusDays(1).format(formatter);
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.START_DATE)
                    .type(DataType.DATE)
                    .lower(request.getStartDate())
                    .upper(nextDayString)
                    .lower_inclusive(true)
                    .upper_inclusive(false)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 교육종료일 범위
        if (StringUtils.isNotBlank(request.getEndDate()) && StringUtils.isNotBlank(request.getEndDate1())) {
            LocalDate upperDate = LocalDate.parse(request.getEndDate1(), formatter);
            String nextDayString = upperDate.plusDays(1).format(formatter);
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(RangeFilter.RangeFilterBuilder()
                    .field(Field.END_DATE)
                    .type(DataType.DATE)
                    .lower(request.getEndDate())
                    .upper(nextDayString)
                    .lower_inclusive(true)
                    .upper_inclusive(false)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 접수상태
        if (StringUtils.isNotBlank(request.getStatus())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getStatus())
                    .field(Field.CONFIRM_STATUS)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 모집대상
        if (StringUtils.isNotBlank(request.getTarget())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermsFilter.TermsFilterBuilder()
                    .terms(Arrays.stream(request.getTarget().split("\\s*,\\s*"))
                        .collect(Collectors.toList()))
                    .field(Field.SIGNIN_TARGET)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 1차 카테고리
        if (StringUtils.isNotBlank(request.getMainCategory())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getMainCategory())
                    .field(Field.MAIN_CATEGORY_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 2차 카테고리
        if (StringUtils.isNotBlank(request.getSubCategory())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getSubCategory())
                    .field(Field.SUB_CATEGORY_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 태그
        if (StringUtils.isNotBlank(request.getTag())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getTag())
                    .field(Field.PROGRAM_TAG)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 교육형식(프로그램형식)
        if (StringUtils.isNotBlank(request.getProgramType())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getProgramType())
                    .field(Field.TYPE_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        // 운영방식
        if (StringUtils.isNotBlank(request.getMethod())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getMethod())
                    .field(Field.METHOD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        boolFilterBuilder.clauses(filterClauses);
        return boolFilterBuilder.build();
    }

    /**
     * 비교과 추천, 검색 병합
     */
    private List<Map<String, Object>> mergeResults(String recommendationJson, List<Map<String, Object>> searchResults) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> recommendations;

        // JSON 문자열 파싱
        try {
            recommendationJson = recommendationJson.replaceAll("\\\\", "");
            recommendationJson = recommendationJson.substring(1, recommendationJson.length() - 1);
            recommendations = mapper.readValue(recommendationJson, new TypeReference<List<Map<String, Object>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return searchResults; // 파싱 실패 시 원본 검색 결과 반환
        }

        // 추천 항목의 키와 순위를 Map으로 저장 (RECOMMEND_TYPE이 1인 항목만)
        Map<String, Integer> recommendedKeysWithRank = new HashMap<>();
        for (Map<String, Object> rec : recommendations) {
            if ("1".equals(rec.get("RECOMMEND_TYPE").toString())) {
                String key = rec.get("RECOMMEND_NONCOURSE_KEY").toString().replace("-", "_");
                int rank = Integer.parseInt(rec.get("RECOMMEND_RANK").toString());
                if (!recommendedKeysWithRank.containsKey(key) || rank < recommendedKeysWithRank.get(key)) {
                    recommendedKeysWithRank.put(key, rank);
                }
            }
        }

        // 추천된 항목과 그렇지 않은 항목을 분리
        Map<String, Map<String, Object>> recommendedResultsMap = new LinkedHashMap<>();
        List<Map<String, Object>> nonRecommendedResults = new ArrayList<>();

        for (Map<String, Object> result : searchResults) {
            String key = result.get("id").toString();
            if (recommendedKeysWithRank.containsKey(key)) {
                result.put("isRecommended", true);
                result.put("recommendRank", recommendedKeysWithRank.get(key));
                recommendedResultsMap.put(key, result);
            } else {
                nonRecommendedResults.add(result);
            }
        }

        // 추천된 항목을 RECOMMEND_RANK 순으로 정렬
        List<Map<String, Object>> recommendedResults = new ArrayList<>(recommendedResultsMap.values());
        recommendedResults.sort(Comparator.comparingInt(a -> (Integer) a.get("recommendRank")));

        // 최종 결과 생성: 추천된 항목을 앞에 두고, 나머지 항목은 원래의 순서(score 순)를 유지
        List<Map<String, Object>> finalResults = new ArrayList<>(recommendedResults);
        finalResults.addAll(nonRecommendedResults);

        return finalResults;
    }

    /**
     * 비교과 return Fields
     */
    private List<String> getNonSubjectReturnFields() {
        return Arrays.asList(
            Field.IDX, Field.TIDX, Field.TYPE_CD, Field.GIDX, Field.TITLE, Field.TOPIC,
            Field.YEAR, Field.SEMESTER, Field.POINT, Field.TIME, Field.COUNT,
            Field.COLG_CD, Field.COLG_NM, Field.DEPT_CD, Field.DEPT_NM,
            Field.MAIN_CATEGORY_CD, Field.MAIN_CATEGORY_NM, Field.SUB_CATEGORY_CD, Field.SUB_CATEGORY_NM,
            Field.MAIN_ABI_NM, Field.SUB_ABI_NM, Field.TYPE_CD, Field.TYPE_NM,
            Field.METHOD_CD, Field.METHOD_NM, Field.PROGRAM_TAG, Field.DEPARTMENT,
            Field.EMAIL, Field.CONTACT, Field.LOCATION, Field.APPLICATION_METHOD,
            Field.ABSTRACT, Field.COVER, Field.START_DATE, Field.END_DATE,
            Field.SIGNIN_START_DATE, Field.SIGNIN_END_DATE, Field.CONFIRM_STATUS,
            Field.IS_COMPLETE, Field.IS_VISIBLE, Field.IS_DELETE,
            Field.REG_DATE_RANK, Field.PARTICIPANT_RATE_RANK, Field.SIGNIN_END_RANK,
            Field.SIGNIN_START_DAY, Field.SIGNIN_END_DAY, Field.START_DAY, Field.END_DAY,
            Field.PARTICIPANT, Field.SIGNIN_LIMIT, Field.D_DAY
        );
    }

    /**
     * 전공 검색
     */
    @Override
    public BaseResponse major(MajorRequest request) {
        //////////////////////////////////////////////////////////////////////
        // 검색조건
        BoolQuery.BoolQueryBuilder boolQueryBuilder = BoolQuery.BoolQueryBuilder();

        List<BoolQuery.Clauses> queryClauses = new ArrayList<>();

        // 검색어(keyword)
        if (StringUtils.isNotBlank(request.getKeyword())) {
            queryClauses.add(BoolQuery.Clauses.builder().
                query(HybridQuery.HybridQueryBuilder()
                    .query(request.getKeyword())
                    .field(Field._SEARCH)
                    .operator(Operator.AND)
                    .tokenizer(Tokenizer.KOR_BIGRAM.getText())
                    .build()).occur(Occur.MUST)
                .build()
            );
        }

        boolQueryBuilder.clauses(queryClauses);
        Query query = boolQueryBuilder.build();

        //////////////////////////////////////////////////////////////////////
        // 필터 조건
        BoolFilter.BoolFilterBuilder boolFilterBuilder = BoolFilter.BoolFilterBuilder();
        List<BoolFilter.Clauses> filterClauses = new ArrayList<>();

        // 대학
        if (request.getUniversity() != null && StringUtils.isNotBlank(request.getUniversity())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getUniversity())
                    .field(Field.COLG_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 학부/학과
        if (request.getDepartment() != null && StringUtils.isNotBlank(request.getDepartment())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getDepartment())
                    .field(Field.DEPT_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }
        // 전공
        if (request.getMajor() != null && StringUtils.isNotBlank(request.getMajor())) {
            filterClauses.add(BoolFilter.Clauses.builder()
                .filter(TermFilter.TermFilterBuilder()
                    .term(request.getMajor())
                    .field(Field.MAJOR_CD)
                    .build())
                .occur(Occur.MUST)
                .build());
        }

        boolFilterBuilder.clauses(filterClauses);
        Filter filter = boolFilterBuilder.build();


        //////////////////////////////////////////////////////////////////////
        // 전공 검색(major)
        //////////////////////////////////////////////////////////////////////
        StandardSearchRequest searchRequest = StandardSearchRequest.builder()
            .indexes(Collections.singletonList(Index.MAJOR))
            .query(query)
            .filter(filter)
            .sort(request.getSort())
            .fields(Arrays.asList(Field.MAJOR_CD
                , Field.COLG_CD
                , Field.COLG_NM
                , Field.DEPT_CD
                , Field.DEPT_NM
                , Field.MAJOR_NM_KOR
                , Field.MAJOR_NM_ENG
                , Field.MAJOR_ABTY
                , Field.MAJOR_INTRO
                , Field.GOAL
                , Field.FIELD
                , Field.TALENT
                , Field.LICENSE_NM
                , Field.GRDT_AF_CARR
                , Field.SUBJECT_NM
                , Field.NON_SBJT_NM
            ))
            .returnFrom(request.getPageNum())
            .returnSize(request.getPagePer())
            .build();

        return getSearchStudioResponse(searchRequest);
    }

    private BaseResponse getSearchStudioResponse(StandardSearchRequest request) {
        printRequestLog("search", request);

        StandardSearchResponse response = searchStudioFeign.standardSearch(request);

        List<Map<String, Object>> documents = getDocuments(response);

        return BaseResponse.builder()
            .result(documents)
            .totalCount(response.getResult().getTotal_hits())
            .build();
    }

    private List<Map<String, Object>> getDocuments(StandardSearchResponse response) {
        return response.getResult().getDocuments().stream()
            .map(document -> {
                Map<String, List<String>> fields = document.getFields();
                Map<String, Object> map = convertMap(fields);

                // 문서의 ID를 추가
                map.put("id", document.getId());
                map.put("score", document.getScore());

                return map;
            })
            .collect(Collectors.toList());
    }

    @Override
    public BaseResponse topicRank(RelatedKeywordsRequest request) {
        Map<String, Object> requestToMap = RequestParamUtil.convertToMap(request);

        TopicRankRequest topicRankRequest = TopicRankRequest.builder()
            .indexes(Collections.singletonList(Index.LECTURE))
            .query(QueryUtil.createQuery(requestToMap))
            .filter(FilterUtil.createFilter(requestToMap))
            .topic(request.getKeyword())
            .max_document_count(300)
            .level(2)
            .top_n_clusters(30)
            .build();

        TopicRankResponse topicRankResponse = searchStudioFeign.topicRank(topicRankRequest);

        List<String> result = topicRankResponse.getResult().getNodes().stream()
            .filter(node -> !node.getName().equalsIgnoreCase(request.getKeyword()))
            .map(node -> replaceUnderscoreWithSpace(node.getName()))
            .collect(Collectors.toList());

        return BaseResponse.builder()
            .result(result)
            .totalCount(result.size())
            .build();
    }

    private String replaceUnderscoreWithSpace(String keyword) {
        return keyword.replaceAll("_", " ");
    }


}


