package com.backend.onboarding.domain.repository;

import com.backend.onboarding.domain.model.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByUsernameAndDeletedAtIsNull(String username);

    UserEntity save(UserEntity UserEntity);
}
