package com.backend.onboarding.service;

import com.backend.onboarding.application.res.ResAuthPostSignupDTOApiV1;
import com.backend.onboarding.application.service.AuthServiceApiV1;
import com.backend.onboarding.domain.model.UserEntity;
import com.backend.onboarding.domain.model.constraint.RoleType;
import com.backend.onboarding.domain.repository.UserRepository;
import com.backend.onboarding.presentation.req.ReqAuthPostSignupDTOApiV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceApiV1Test {

    @InjectMocks
    private AuthServiceApiV1 authServiceApiV1;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void testSignupSuccess() {

        // Given
        ReqAuthPostSignupDTOApiV1 reqDto = ReqAuthPostSignupDTOApiV1.builder()
                .username("testuser")
                .password("Test123!")
                .nickname("testnickname")
                .build();

        String encodedPassword = reqDto.getPassword();

        UserEntity savedUser = UserEntity.create(
                reqDto.getUsername(),
                encodedPassword,
                reqDto.getNickname(),
                RoleType.USER
        );

        given(userRepository.findByUsername(reqDto.getUsername())).willReturn(Optional.empty());
        given(passwordEncoder.encode(reqDto.getPassword())).willReturn(encodedPassword);
        given(userRepository.save(any(UserEntity.class))).willReturn(savedUser);

        // When
        ResAuthPostSignupDTOApiV1 response = authServiceApiV1.signup(reqDto);

        // Then
        assertThat(response.getUsername()).isEqualTo(reqDto.getUsername());
        assertThat(response.getNickname()).isEqualTo(reqDto.getNickname());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 중복된 아이디")
    public void testSignupFailure() {

        // Given
        ReqAuthPostSignupDTOApiV1 reqDto = ReqAuthPostSignupDTOApiV1.builder()
                .username("testuser")
                .password("Test123!")
                .nickname("testnickname")
                .build();

        UserEntity existingUser = UserEntity.create(
                reqDto.getUsername(),
                "encodedPassword",
                reqDto.getNickname(),
                RoleType.USER
        );

        given(userRepository.findByUsername(reqDto.getUsername())).willReturn(Optional.of(existingUser));

        // When & Then
        assertThatThrownBy(
                () -> authServiceApiV1.signup(reqDto))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("이미 존재하는 아이디입니다.");
    }
}
