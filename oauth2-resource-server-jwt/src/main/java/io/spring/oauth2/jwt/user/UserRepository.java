package io.spring.oauth2.jwt.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailOrUsername(String emailOrUsername1, String emailOrUsername2);
    Optional<User> findByEmail(String email);
}