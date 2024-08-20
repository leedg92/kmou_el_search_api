package com.saltlux.searchstudio.api.util;

import static com.saltlux.searchstudio.api.util.CommonUtil.isPresentAndNotBlank;

import com.saltlux.searchstudio.api.ApiConstants;
import com.saltlux.searchstudio.api.ApiConstants.Field;
import com.saltlux.searchstudio.api.feign.request.enums.DataType;
import com.saltlux.searchstudio.api.feign.request.enums.Occur;
import com.saltlux.searchstudio.api.feign.request.filter.BoolFilter;
import com.saltlux.searchstudio.api.feign.request.filter.Filter;
import com.saltlux.searchstudio.api.feign.request.filter.RangeFilter;
import com.saltlux.searchstudio.api.feign.request.filter.TermFilter;
import com.saltlux.searchstudio.api.feign.request.filter.TermsFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterUtil {

    public static Filter createFilter(Map<String, Object> request) {
        BoolFilter.BoolFilterBuilder boolFilterBuilder = BoolFilter.BoolFilterBuilder();

        List<BoolFilter.Clauses> filterClauses = new ArrayList<>();

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_ABO_YN)) { // 폐강여부
            filterClauses.add(createBoolFilterClause(
                TermFilter.TermFilterBuilder()
                    .term(request.get(ApiConstants.PARAMETER_ABO_YN).toString())
                    .field(Field.ABO_YN)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_START_DATE)    // 개설연도
            && isPresentAndNotBlank(request, ApiConstants.PARAMETER_END_DATE)) {
            filterClauses.add(createBoolFilterClause(
                RangeFilter.RangeFilterBuilder()
                    .field(Field.YEAR)
                    .type(DataType.INTEGER)
                    .lower(request.get(ApiConstants.PARAMETER_START_DATE).toString())
                    .upper(request.get(ApiConstants.PARAMETER_END_DATE).toString())
                    .lower_inclusive(true)
                    .upper_inclusive(true)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_UNIVERSITY)) { // 대학
            filterClauses.add(createBoolFilterClause(
                TermFilter.TermFilterBuilder()
                    .term(request.get(ApiConstants.PARAMETER_UNIVERSITY).toString())
                    .field(Field.COLG_CD)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_DEPARTMENT)) { // 학부/학과
            filterClauses.add(createBoolFilterClause(
                TermFilter.TermFilterBuilder()
                    .term(request.get(ApiConstants.PARAMETER_DEPARTMENT).toString())
                    .field(Field.DEPT_CD)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_MAJOR)) {  // 전공
            filterClauses.add(createBoolFilterClause(
                TermFilter.TermFilterBuilder()
                    .term(request.get(ApiConstants.PARAMETER_MAJOR).toString())
                    .field(Field.MAJOR_CD)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_GRADE)) {  // 학년
            filterClauses.add(createBoolFilterClause(
                RangeFilter.RangeFilterBuilder()
                    .field(Field.GRADE)
                    .type(DataType.INTEGER)
                    .lower(request.get(ApiConstants.PARAMETER_GRADE).toString())
                    .upper(request.get(ApiConstants.PARAMETER_GRADE).toString())
                    .lower_inclusive(true)
                    .upper_inclusive(true)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_SEMESTER)) {   // 학기
            filterClauses.add(createBoolFilterClause(
                TermFilter.TermFilterBuilder()
                    .term(request.get(ApiConstants.PARAMETER_SEMESTER).toString())
                    .field(Field.SMT_CD)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_COURSE_CLASSIFICATION)) {  // 이수구분(전공필수,전공선택...)
            filterClauses.add(createBoolFilterClause(
                TermsFilter.TermsFilterBuilder()
                    .terms(Arrays.stream(
                            request.get(ApiConstants.PARAMETER_COURSE_CLASSIFICATION).toString().split("\\s*,\\s*"))
                        .collect(Collectors.toList()))
                    .field(Field.COMDIV_CD)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_CORE_COMPETENCE)) {    // 핵심역량
            filterClauses.add(createBoolFilterClause(
                TermsFilter.TermsFilterBuilder()
                    .terms(Arrays.stream(
                            request.get(ApiConstants.PARAMETER_CORE_COMPETENCE).toString().split("\\s*,\\s*"))
                        .collect(Collectors.toList()))
                    .field(Field.MAIN_ABI_NM)
                    .build(),
                Occur.MUST));
        }

        if (isPresentAndNotBlank(request, ApiConstants.PARAMETER_MAJOR_CLASSIFICATION)) {   // 전공구분
            filterClauses.add(createBoolFilterClause(
                TermsFilter.TermsFilterBuilder()
                    .terms(Arrays.stream(
                            request.get(ApiConstants.PARAMETER_MAJOR_CLASSIFICATION).toString().split("\\s*,\\s*"))
                        .collect(Collectors.toList()))
                    .field("")// TODO: 구현
                    .build(),
                Occur.MUST));
        }

        boolFilterBuilder.clauses(filterClauses);

        return boolFilterBuilder.build();
    }

    public static BoolFilter.Clauses createBoolFilterClause(Filter filter, Occur occur) {
        return BoolFilter.Clauses.builder()
            .filter(filter)
            .occur(occur)
            .build();
    }

}
