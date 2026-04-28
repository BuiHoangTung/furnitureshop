package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsUserByEmail(String email);
    Optional<UserEntity> findUserEntitiesByEmail(String email);
    Optional<UserEntity> findUserEntitiesById(long id);
}
