package com.backend.onboarding.application.service;

import com.backend.onboarding.application.res.ResAuthPostSignupDTOApiV1;
import com.backend.onboarding.domain.model.UserEntity;
import com.backend.onboarding.domain.model.constraint.RoleType;
import com.backend.onboarding.domain.repository.UserRepository;
import com.backend.onboarding.presentation.req.ReqAuthPostSignupDTOApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceApiV1 {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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

    private void checkUsernameDuplication(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
        if (userEntityOptional.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }
}
