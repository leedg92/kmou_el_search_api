package com.saltlux.searchstudio.api.service;

import com.saltlux.searchstudio.api.controller.request.MajorRequest;
import com.saltlux.searchstudio.api.controller.request.NonSubjectRequest;
import com.saltlux.searchstudio.api.controller.request.ProfessorRequest;
import com.saltlux.searchstudio.api.controller.request.RelatedKeywordsRequest;
import com.saltlux.searchstudio.api.controller.request.SubjectRequest;
import com.saltlux.searchstudio.api.service.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    BaseResponse subject(SubjectRequest request);

    BaseResponse nonSubject(NonSubjectRequest request);

    BaseResponse professor(ProfessorRequest request);

    BaseResponse major(MajorRequest request);

    BaseResponse topicRank(RelatedKeywordsRequest request);

}
