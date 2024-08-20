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
 * 비교과
 */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NonSubjectRequest {

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
     * 시작날짜(시작)
     */
    private String startDate;
    /**
     * 시작날짜(끝)
     */
    private String startDate1;
    /**
     * 종료날짜(시작)
     */
    private String endDate;
    /**
     * 종료날짜(끝)
     */
    private String endDate1;
    /**
     * 신청시작날짜(시작)
     */
    private String signInStartDate;
    /**
     * 신청시작날짜1(끝)
     */
    private String signInStartDate1;
    /**
     * 신청종료날짜(시작)
     */
    private String signInEndDate;
    /**
     * 신청종료날짜1(끝)
     */
    private String signInEndDate1;
    /**
     * 접수상태
     */
    private String status;
    /**
     * 모집대상
     */
    private String target;
    /**
     * 1차 카테고리
     */
    private String mainCategory;
    /**
     * 2차 카테고리
     */
    private String subCategory;
    /**
     * 태그
     */
    private String tag;
    /**
     * 프로그램형식
     */
    private String programType;
    /**
     * 운영방식
     */
    private String method;
    /**
     * 참가자순
     */
    private String participantRateRank;
    /**
     * 등록순
     */
    private String regDateRank;
    /**
     * 신청마감 임박순
     */
    private String signinEndRank;
    /**
     * 학번(추천순에 사용)
     */
    private String studentNo;

    @Builder
    private NonSubjectRequest(String keyword, Integer pageNum, Integer pagePer, List<Sort> sort, String startDate, String startDate1,
        String endDate, String endDate1, String status, String target, String mainCategory, String subCategory, String tag,
        String signInStartDate, String signInStartDate1, String signInEndDate, String signInEndDate1, String programType, String method,
        String participantRateRank, String regDateRank, String signinEndRank, String studentNo) {
        this.keyword = keyword;
        this.pageNum = pageNum;
        this.pagePer = pagePer;
        this.sort = sort;
        this.startDate = startDate;
        this.startDate1 = startDate1;
        this.endDate = endDate;
        this.endDate1 = endDate1;
        this.status = status;
        this.target = target;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.tag = tag;
        this.signInStartDate = signInStartDate;
        this.signInStartDate1 = signInStartDate1;
        this.signInEndDate = signInEndDate;
        this.signInEndDate1 = signInEndDate1;
        this.programType = programType;
        this.method = method;
        this.participantRateRank = participantRateRank;
        this.regDateRank = regDateRank;
        this.signinEndRank = signinEndRank;
        this.studentNo = studentNo;
    }
}
