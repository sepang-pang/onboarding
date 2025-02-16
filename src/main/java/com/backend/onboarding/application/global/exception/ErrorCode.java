package com.backend.onboarding.application.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    AUTHORITY_EXCEPTION(HttpStatus.FORBIDDEN, 2001), // 권한 검증 에러
    ENTITY_ALREADY_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, 2002), // 중복 검증 에러
    ENTITY_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 2003), // 존재하지 않는 객체 에러
    BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, 2004); // 기타 에러

    private final HttpStatus httpStatus;
    private final Integer code;
}
