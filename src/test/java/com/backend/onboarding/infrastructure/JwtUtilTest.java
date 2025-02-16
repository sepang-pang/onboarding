package com.backend.onboarding.infrastructure;

import com.backend.onboarding.domain.model.constraint.RoleType;
import com.backend.onboarding.infrastructure.utl.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private String secretKey;

    @BeforeEach
    void setUp() {
        secretKey = Base64.getEncoder().encodeToString("test-secret-key-1234567890123456".getBytes());
        jwtUtil.secretKey = secretKey;
        jwtUtil.init();
    }


    @Nested
    @DisplayName("1. Secret Key 검증")
    class SecretKeyTest {

        @Test
        @DisplayName("Base64 인코딩이 정상적으로 수행되는지 테스트")
        void testBase64Encoding() {
            String plainText = "test-secret-key-1234567890123456";
            String encodedText = Base64.getEncoder().encodeToString(plainText.getBytes());
            assertThat(secretKey).isEqualTo(encodedText);
        }
    }

    @Nested
    @DisplayName("2. JWT 생성 및 검증 테스트")
    class JwtGenerationAndValidationTest {

        private String accessToken;
        private String refreshToken;

        @BeforeEach
        void generateTokens() {
            accessToken = jwtUtil.generateAccessJwt("testUser", RoleType.USER);
            refreshToken = jwtUtil.generateRefreshJwt();
        }

        @Test
        @DisplayName("Access Token이 정상적으로 생성되는지 테스트")
        void testAccessTokenGeneration() {
            assertThat(accessToken).isNotNull();
            assertThat(accessToken).startsWith(JwtUtil.BEARER_PREFIX);
        }

        @Test
        @DisplayName("Refresh Token이 정상적으로 생성되는지 테스트")
        void testRefreshTokenGeneration() {
            assertThat(refreshToken).isNotNull();
        }
    }

    @Nested
    @DisplayName("3. JWT 유효성 검증 테스트")
    class JwtValidationTest {

        private String validAccessToken;

        @BeforeEach
        void generateValidToken() {
            validAccessToken = jwtUtil.generateAccessJwt("testUser", RoleType.USER).substring(7);
        }

        @Test
        @DisplayName("Signature 검증이 정상적으로 수행되는지 테스트")
        void testValidSignature() {
            assertTrue(jwtUtil.validateToken(validAccessToken));
        }

        @Test
        @DisplayName("잘못된 서명을 가진 JWT가 검증 실패하는지 테스트")
        void testInvalidSignature() {
            String invalidToken = validAccessToken + "tampered";
            assertFalse(jwtUtil.validateToken(invalidToken));
        }
    }

    @Nested
    @DisplayName("4. JWT 만료 테스트")
    class JwtExpirationTest {

        private String expiredToken;

        @BeforeEach
        void generateExpiredToken() {
            expiredToken = Jwts.builder()
                    .setSubject("testUser")
                    .claim(JwtUtil.AUTHORIZATION_KEY, RoleType.USER)
                    .setExpiration(new Date(System.currentTimeMillis() - 1000))
                    .setIssuedAt(new Date(System.currentTimeMillis() - 2000))
                    .signWith(jwtUtil.key, SignatureAlgorithm.HS256)
                    .compact();
        }

        @Test
        @DisplayName("JWT가 만료된 경우 검증 실패하는지 테스트")
        void testExpiredToken() {
            assertFalse(jwtUtil.validateToken(expiredToken));
        }
    }
}
