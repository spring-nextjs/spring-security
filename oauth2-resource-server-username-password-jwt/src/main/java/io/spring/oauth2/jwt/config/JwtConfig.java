package io.spring.oauth2.jwt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.resourceserver.jwt.expiration-seconds-access-token}")
    private long accessExpiration;

    @Value("${spring.security.oauth2.resourceserver.jwt.expiration-seconds-refresh-token}")
    private long refreshExpiration;
}