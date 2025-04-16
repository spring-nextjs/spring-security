package io.spring.oauth2.jwt.jwt;

import io.spring.oauth2.jwt.common.payload.exception.InvalidAuthenticationException;
import io.spring.oauth2.jwt.config.JwtConfig;
import io.spring.oauth2.jwt.token.TokenService;
import io.spring.oauth2.jwt.token.TokenType;
import io.spring.oauth2.jwt.user.User;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
record JwtServiceImpl(
        JwtConfig jwtConfig,
        JwtEncoder jwtEncoder,
        JwtDecoder jwtDecoder,
        TokenService tokenService
) implements JwtService {

    /**
     * Generates a JWT token based on the provided authentication details and token type.
     *
     * @param user the user for which to create the JWT token
     * @param tokenType the type of the JWT token (either access or refresh)
     * @return a JWT token as a String
     */
    @Override
    public String createJwt(User user, TokenType tokenType) {
        JwtClaimsSet claims = createJwtClaimsSet(user, tokenType, UUID.randomUUID());
        String jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        tokenService.saveValidToken(
                UUID.fromString(claims.getId()),
                user.getId(),
                jwt,
                tokenType);
        return jwt;
    }

    /**
     * Creates a JWT claims set based on the provided email and permissions.
     *
     * @param user the user for which to create the JWT claims set
     * @param tokenType the JWT type (either access or refresh)
     * @param jwtId the JWT ID
     * @return a JWT claims set
     */
    private JwtClaimsSet createJwtClaimsSet(User user, TokenType tokenType, UUID jwtId) {
        Instant now = Instant.now();
        long expirationSeconds = tokenType == TokenType.ACCESS ? jwtConfig.getAccessExpiration() : jwtConfig.getRefreshExpiration();

        String[] scope = user
                .getAuthorities().stream()
                .map(Object::toString).toArray(String[]::new);

        return JwtClaimsSet.builder()
                .id(jwtId.toString())
                .issuer(jwtConfig.getIssuerUri())
                .issuedAt(now)
                .expiresAt(now.plus(expirationSeconds, ChronoUnit.SECONDS))
                .subject(user.getId().toString())
                .claim(StandardClaimNames.EMAIL, user.getEmail())
                .claim(StandardClaimNames.PREFERRED_USERNAME, user.getUsername())
                .claim("scope", scope)
                .build();
    }

    /**
     * Extracts the email from the given JWT token.
     *
     * @param jwt the JWT token
     * @return the email extracted from the JWT token
     */
    @Override
    public String extractEmailFromJwt(String jwt, TokenType tokenType) {
        Jwt decodedJwt = this.getDecodedJwt(jwt);
        if (decodedJwt == null) {
            return null;
        }

        String email = decodedJwt.getClaim(StandardClaimNames.EMAIL);
        if (email == null) {
            tokenService.invalidateToken(extractIdFromJwt(jwt, tokenType), tokenType);
            return null;
        }
        return email;
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param jwt the JWT token
     * @return the expiration date extracted from the JWT token
     */
    private Date extractExpirationFromJwt(String jwt) {
        Instant expiresAt = getDecodedJwt(jwt).getExpiresAt();
        if (expiresAt == null) {
            return null;
        }
        return Date.from(expiresAt);
    }

    /**
     * Extracts the JWT ID (jti) from the given JWT token.
     *
     * @param jwt the JWT token from which to extract the Key ID
     * @return the JWT ID extracted from the JWT token, or null if the JWT ID is not present
     */
    @Override
    public UUID extractIdFromJwt(String jwt, TokenType tokenType) {
        Jwt decodedJwt = this.getDecodedJwt(jwt);
        if (decodedJwt == null) {
            return null;
        }

        Object jwtId = decodedJwt.getId();
        if (jwtId == null) {
            tokenService.invalidateToken(extractIdFromJwt(jwt, tokenType), tokenType);
            return null;
        }
        return UUID.fromString(jwtId.toString());
    }

    /**
     * Extracts the issuer from the given JWT token.
     *
     * @param jwt the JWT token
     * @return the issuer extracted from the JWT token
     */
    private String extractIssuerFromJwt(String jwt) {
        URL issuerUrl = getDecodedJwt(jwt).getIssuer();
        if (issuerUrl == null) {
            return null;
        }
        return issuerUrl.toString();
    }

    /**
     * Extracts the subject (typically the user id) from the given JWT token.
     *
     * @param jwt the JWT token
     * @return the subject (user ID) extracted from the JWT token
     */
    private UUID extractSubjectFromJwt(String jwt, TokenType tokenType) {
        Jwt decodedJwt = this.getDecodedJwt(jwt);
        if (decodedJwt == null) {
            return null;
        }

        String subject =  decodedJwt.getSubject();
        if (subject == null) {
            tokenService.invalidateToken(extractIdFromJwt(jwt, tokenType), tokenType);
            return null;
        }
        return UUID.fromString(subject);
    }

    /**
     * Decodes the given JWT token.
     * If the token is invalid, it is invalidated and null is returned.
     *
     * @param jwt the JWT token to decode
     * @return the decoded JWT token
     */
    private Jwt getDecodedJwt(String jwt) {
        try {
            return jwtDecoder.decode(jwt);
        } catch (JwtException ex) {
            throw new InvalidAuthenticationException("jwt.token.invalid", new String[]{ex.getLocalizedMessage()});
        }
    }

    /**
     * Checks if the given JWT token has valid claims.
     *
     * @param jwt the JWT token
     * @param tokenType the type of the JWT token
     * @return true if the claims are valid, false otherwise
     */
    private boolean isJwtClaimsValid(String jwt, TokenType tokenType) {
        return isJwtSubjectValid(jwt, tokenType)
                && isJwtIssuerValid(jwt)
                && !isJwtExpired(jwt)
                && tokenService.isTokenValid(extractIdFromJwt(jwt, tokenType), tokenType);
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param jwt the JWT token
     * @return true if the JWT token is expired, false otherwise
     */
    private boolean isJwtExpired(String jwt) {
        Date expirationDate = extractExpirationFromJwt(jwt);
        return expirationDate == null || expirationDate.before(new Date());
    }

    /**
     * Checks if the issuer of the given JWT token is valid.
     *
     * @param jwt the JWT token
     * @return true if the issuer is valid, false otherwise
     */
    private boolean isJwtIssuerValid(String jwt) {
        final String jwtIssuer = extractIssuerFromJwt(jwt);
        return StringUtils.hasText(jwtIssuer) && jwtIssuer.equals(jwtConfig.getIssuerUri());
    }

    /**
     * Checks if the subject of the given JWT token is valid.
     *
     * @param jwt the JWT token
     * @param tokenType the type of the JWT token
     * @return true if the subject is valid, false otherwise
     */
    private boolean isJwtSubjectValid(String jwt, TokenType tokenType) {
        final UUID jwtSubject = extractSubjectFromJwt(jwt, tokenType);
        return jwtSubject != null;
    }

    /**
     * Validates the given Access token.
     *
     * @param token the JWT token
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean isJwtValid(String token, TokenType tokenType) {
        if (!isJwtClaimsValid(token, tokenType)) {
            tokenService.invalidateToken(extractIdFromJwt(token, tokenType), tokenType);
        }
        return true;
    }
}