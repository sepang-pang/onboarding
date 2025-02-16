package com.backend.onboarding.application.global.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends AuthException {
    public EntityNotFoundException(String message) {
        super(ErrorCode.ENTITY_NOT_FOUND_ERROR, message);
    }
}
