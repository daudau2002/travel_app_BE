package com.travel_app.travel.repository;

import com.travel_app.travel.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity, Long> {
    UserEntity findOneByUsername(String username);
    UserEntity findOneByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserEntity findOneById(Long id);
}
