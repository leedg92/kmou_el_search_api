package com.saltlux.searchstudio.api.feign.request.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tokenizer {

    KOREAN("korean"),
    ENGLISH("english"),
    JAPANESE("japanese"),
    CHINESE("chinese"),
    WHITESPACE("whitespace"),
    KOR_BIGRAM("KOR_BIGRAM"),
    ENG_BIGRAM("ENG_BIGRAM"),
    JPN_BIGRAM("JPN_BIGRAM"),
    CHI_BIGRAM("CHI_BIGRAM"),
    BIGRAM("BIGRAM");

    private final String text;
}
