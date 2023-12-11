package com.developaw.harupuppy.domain.user.repository;

import com.developaw.harupuppy.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
