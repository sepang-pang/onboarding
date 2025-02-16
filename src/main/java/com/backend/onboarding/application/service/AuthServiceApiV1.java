package com.backend.onboarding.application.service;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceApiV1 {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisRefreshTokenService redisRefreshTokenService;

    @Transactional
    public ResAuthPostSignupDTOApiV1 signup(ReqAuthPostSignupDTOApiV1 dto) {

        checkUsernameDuplication(dto.getUsername());

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

        UserEntity userEntity = userRepository.findByUsernameAndDeletedAtIsNull(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 정확히 입력해주세요."));

        validatePasswordMatches(dto, userEntity);

        String accessJwt = jwtUtil.generateAccessJwt(userEntity.getUsername(), userEntity.getRole());
        String refreshJwt = jwtUtil.generateRefreshJwt();

        // Redis 에 Refresh 토큰 저장
        redisRefreshTokenService.saveRefreshToken(userEntity.getUsername(), refreshJwt);

        // 리프레시 토큰 쿠키에 담기
        response.addCookie(jwtUtil.generateRefreshJwtCookie(refreshJwt));

        return ResAuthPostLoginDTOApiV1.of(accessJwt);
    }

    private void checkUsernameDuplication(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
        if (userEntityOptional.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    private void validatePasswordMatches(ReqAuthPostLoginDTOApiV1 dto, UserEntity userEntity) {
        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 정확히 입력해주세요.");
        }
    }
}
