package com.developaw.harupuppy.domain.user.repository;

import com.developaw.harupuppy.domain.user.domain.Home;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<Home, Long> {
    Optional<Home> findByHomeId(String homeId);
}
