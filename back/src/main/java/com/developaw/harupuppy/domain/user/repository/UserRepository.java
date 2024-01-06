package com.developaw.harupuppy.domain.user.repository;

import com.developaw.harupuppy.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserId (Long userId);
    Optional<User> findByEmail(String email);
}
