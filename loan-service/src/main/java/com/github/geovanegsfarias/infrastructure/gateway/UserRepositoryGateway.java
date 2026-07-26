package com.github.geovanegsfarias.infrastructure.gateway;

import com.github.geovanegsfarias.core.entities.User;
import com.github.geovanegsfarias.core.gateway.UserGateway;
import com.github.geovanegsfarias.infrastructure.mapper.UserEntityMapper;
import com.github.geovanegsfarias.infrastructure.persistence.UserRepository;

import java.util.Optional;

public class UserRepositoryGateway implements UserGateway {

    private final UserRepository userRepository;
    private final UserEntityMapper entityMapper;

    public UserRepositoryGateway(UserRepository userRepository, UserEntityMapper entityMapper) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public User save(User user) {
        var entity = entityMapper.toEntity(user);
        var savedUserEntity = userRepository.save(entity);
        return entityMapper.toDomain(savedUserEntity);
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email).map(entityMapper::toDomain);
    }

    @Override
    public Boolean existsByEmailIgnoreCase(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
