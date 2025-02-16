package com.backend.onboarding.application.service;

public interface RedisRefreshTokenService {

    void saveRefreshToken(String username, String refreshToken);

    String getRefreshToken(String username);

    void deleteRefreshToken(String username);
}
