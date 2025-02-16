package com.backend.onboarding.application.global.exception;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends AuthException {
    public UnauthorizedAccessException(String message) {
        super(ErrorCode.AUTHORITY_EXCEPTION, message);
    }
}
