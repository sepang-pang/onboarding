package com.backend.onboarding.application.service;

import com.backend.onboarding.application.global.exception.DuplicateResourceException;
import com.backend.onboarding.application.global.exception.EntityNotFoundException;
import com.backend.onboarding.application.res.ResAuthPostLoginDTOApiV1;
import com.backend.onboarding.application.res.ResAuthPostSignupDTOApiV1;
import com.backend.onboarding.domain.model.UserEntity;
import com.backend.onboarding.domain.model.constraint.RoleType;
import com.backend.onboarding.domain.repository.UserRepository;
import com.backend.onboarding.infrastructure.utl.JwtUtil;
import com.backend.onboarding.presentation.req.ReqAuthPostLoginDTOApiV1;
import com.backend.onboarding.presentation.req.ReqAuthPostSignupDTOApiV1;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthServiceApiV1")
public class AuthServiceApiV1 {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisRefreshTokenServiceV1 redisRefreshTokenServiceV1;

    @Transactional
    public ResAuthPostSignupDTOApiV1 signup(ReqAuthPostSignupDTOApiV1 dto) {

        checkForDuplicateUsername(dto.getUsername());

        UserEntity userEntityForSaving = UserEntity.create(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getNickname(),
                RoleType.USER
        );

        return ResAuthPostSignupDTOApiV1.of(
                userRepository.save(userEntityForSaving)
        );
    }

    @Transactional
    public ResAuthPostLoginDTOApiV1 sign(ReqAuthPostLoginDTOApiV1 dto, HttpServletResponse response) {

        UserEntity userEntity = getUserEntityByUsername(dto.getUsername());
        log.info("사용자 '{}' 로그인 시도", userEntity.getUsername());

        validatePasswordMatches(dto, userEntity);

        String accessJwt = jwtUtil.generateAccessJwt(userEntity.getUsername(), userEntity.getRole());

        // Redis 에 Refresh Token 저장
        if (redisRefreshTokenServiceV1.getRefreshToken(userEntity.getUsername()) == null) {
            String refreshJwt = jwtUtil.generateRefreshJwt();
            redisRefreshTokenServiceV1.saveRefreshToken(userEntity.getUsername(), refreshJwt);
            response.addCookie(jwtUtil.generateRefreshJwtCookie(refreshJwt));
        }

        // Access Token 헤더에 담기
        response.addHeader(JwtUtil.AUTHORIZATION_KEY, accessJwt);

        log.info("사용자 '{}' 로그인 성공, Access Token 발급 완료", userEntity.getUsername());

        return ResAuthPostLoginDTOApiV1.of(accessJwt);
    }

    private void checkForDuplicateUsername(String username) {
        getUserEntityOptionalByUsername(username)
                .ifPresent(user -> {
                    throw new DuplicateResourceException("이미 존재하는 아이디입니다.");
                });
    }

    private UserEntity getUserEntityByUsername(String username) {
        return getUserEntityOptionalByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("아이디를 정확히 입력해주세요."));
    }

    private Optional<UserEntity> getUserEntityOptionalByUsername(String username) {
        return userRepository.findByUsernameAndDeletedAtIsNull(username);
    }

    private void validatePasswordMatches(ReqAuthPostLoginDTOApiV1 dto, UserEntity userEntity) {
        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 정확히 입력해주세요.");
        }
    }
}
