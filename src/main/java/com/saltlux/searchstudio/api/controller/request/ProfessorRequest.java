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
 * 교수
 */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfessorRequest {

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
     * 시작날짜
     */
    @NotBlank
    private String startDate;
    /**
     * 종료날짜
     */
    @NotBlank
    private String endDate;
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

    @Builder
    private ProfessorRequest(String keyword, Integer pageNum, Integer pagePer, List<Sort> sort, String startDate,
        String endDate, String university,
        String department, String major) {
        this.keyword = keyword;
        this.pageNum = pageNum;
        this.pagePer = pagePer;
        this.sort = sort;
        this.startDate = startDate;
        this.endDate = endDate;
        this.university = university;
        this.department = department;
        this.major = major;
    }
}
