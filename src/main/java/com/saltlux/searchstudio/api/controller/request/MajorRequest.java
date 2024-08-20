package com.saltlux.searchstudio.api.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.saltlux.searchstudio.api.feign.request.common.Sort;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전공
 */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MajorRequest {

    /**
     * 검색어
     */
    @NotBlank
    private String keyword;
    /**
     * 요청 페이지 번호
     */
    @PositiveOrZero
    private Integer pageNum;
    /**
     * 페이지당 목록 수
     */
    @Positive
    private Integer pagePer;
    /**
     * 정렬 순서
     */
    private List<Sort> sort;
    /**
     * 대학코드
     */
    private String university;
    /**
     * 학과코드
     */
    private String department;
    /**
     * 전공코드
     */
    private String major;
    /**
     * 전공구분
     */
    private List<String> majorClassification;
    /**
     * 교과과정
     */
    private List<String> curriculumClassification;

    @Builder
    private MajorRequest(String keyword, Integer pageNum, Integer pagePer, List<Sort> sort, String university,
        String department, String major, List<String> majorClassification,
        List<String> curriculumClassification) {
        this.keyword = keyword;
        this.pageNum = pageNum;
        this.pagePer = pagePer;
        this.sort = sort;
        this.university = university;
        this.department = department;
        this.major = major;
        this.majorClassification = majorClassification;
        this.curriculumClassification = curriculumClassification;
    }
}
