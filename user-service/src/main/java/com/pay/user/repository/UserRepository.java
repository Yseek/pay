package com.pay.user.repository;

import com.pay.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByEmail(String email);
    Optional<UserProfile> findByEmail(String email);
    void deleteByEmail(String email);
}
