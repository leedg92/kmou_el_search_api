package com.saltlux.searchstudio.api.controller;

import static com.saltlux.searchstudio.api.ApiConstants.LOG_FORMAT_TIME_CHECK;
import static com.saltlux.searchstudio.api.util.CommonUtil.printRequestLog;

import com.saltlux.searchstudio.api.ApiResponse;
import com.saltlux.searchstudio.api.controller.request.MajorRequest;
import com.saltlux.searchstudio.api.controller.request.NonSubjectRequest;
import com.saltlux.searchstudio.api.controller.request.ProfessorRequest;
import com.saltlux.searchstudio.api.controller.request.RelatedKeywordsRequest;
import com.saltlux.searchstudio.api.controller.request.SubjectRequest;
import com.saltlux.searchstudio.api.service.SearchService;
import com.saltlux.searchstudio.api.service.response.BaseResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/subject")
    public ApiResponse subject(@RequestBody @Valid SubjectRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("search subject");
        
        BaseResponse response = searchService.subject(request);

        stopWatch.stop();
        log.debug(LOG_FORMAT_TIME_CHECK, stopWatch.getLastTaskInfo().getTaskName(), DurationFormatUtils.formatDurationHMS(stopWatch.getLastTaskInfo().getTimeMillis()));

        return ApiResponse.ok(response);
    }

    @PostMapping("/non-subject")
    public ApiResponse nonSubject(@RequestBody @Valid NonSubjectRequest request) {
        BaseResponse response = searchService.nonSubject(request);

        return ApiResponse.ok(response);
    }

    @PostMapping("/professor")
    public ApiResponse professor(@RequestBody @Valid ProfessorRequest request) {
        BaseResponse response = searchService.professor(request);

        return ApiResponse.ok(response);
    }

    @PostMapping("/major")
    public ApiResponse major(@RequestBody @Valid MajorRequest request) {
        BaseResponse response = searchService.major(request);

        return ApiResponse.ok(response);
    }

    @PostMapping("/related/keywords")
    public ApiResponse keyword(@RequestBody @Valid RelatedKeywordsRequest request) {
        BaseResponse response = searchService.topicRank(request);

        return ApiResponse.ok(response);
    }

}
