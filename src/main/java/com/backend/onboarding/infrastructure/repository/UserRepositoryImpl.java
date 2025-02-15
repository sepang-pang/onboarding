package com.backend.onboarding.infrastructure.repository;

import com.backend.onboarding.domain.model.UserEntity;
import com.backend.onboarding.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByUsernameAndDeletedAtIsNull(String username) {
        return jpaUserRepository.findByUsernameAndDeletedAtIsNull(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return jpaUserRepository.save(userEntity);
    }

}
