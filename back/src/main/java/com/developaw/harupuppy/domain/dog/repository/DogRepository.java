package com.developaw.harupuppy.domain.dog.repository;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findByDogId (Long dogId);
}
