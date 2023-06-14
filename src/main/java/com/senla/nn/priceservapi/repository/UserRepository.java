package com.senla.nn.priceservapi.repository;

import com.senla.nn.priceservapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByUsername(String name);
    boolean existsByUsername(String name);
}
