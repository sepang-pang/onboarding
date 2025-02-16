package com.backend.onboarding.application.global.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends AuthException {
    public DuplicateResourceException(String message) {
        super(ErrorCode.ENTITY_ALREADY_EXIST_EXCEPTION, message);
    }
}
