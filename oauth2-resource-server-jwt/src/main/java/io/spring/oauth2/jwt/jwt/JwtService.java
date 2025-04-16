package io.spring.oauth2.jwt.jwt;

import io.spring.oauth2.jwt.token.TokenType;
import io.spring.oauth2.jwt.user.User;

import java.util.UUID;

/**
 * @since alpha 0.0.1
 * @see JwtServiceImpl
 */
public interface JwtService {
    String createJwt(User user, TokenType tokenType);
    String extractEmailFromJwt(String jwt, TokenType tokenType);
    UUID extractIdFromJwt(String jwt, TokenType tokenType);
    boolean isJwtValid(String jwt, TokenType tokenType);
}