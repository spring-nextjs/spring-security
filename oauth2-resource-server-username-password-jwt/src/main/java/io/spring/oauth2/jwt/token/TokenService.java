package io.spring.oauth2.jwt.token;

import java.util.UUID;

/**
 * @since alpha 0.0.1
 * @see TokenServiceImpl
 */
public interface TokenService {
    void invalidateToken(UUID jwtId, TokenType tokenType);
    void invalidateAllTokensByUserId(UUID userId);
    boolean isTokenValid(UUID jwtId, TokenType tokenType);
    void saveValidToken(UUID jwtId, UUID userId, String token, TokenType tokenType);
}