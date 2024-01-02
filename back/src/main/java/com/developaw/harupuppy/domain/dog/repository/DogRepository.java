package com.developaw.harupuppy.domain.dog.repository;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
