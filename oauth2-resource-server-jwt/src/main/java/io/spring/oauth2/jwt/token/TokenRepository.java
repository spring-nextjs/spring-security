package io.spring.oauth2.jwt.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token> findByUserIdAndValidTrue(UUID userId);
    Optional<Token> findByJwtIdAndTypeAndValidTrue(UUID jwtId, TokenType type);
}