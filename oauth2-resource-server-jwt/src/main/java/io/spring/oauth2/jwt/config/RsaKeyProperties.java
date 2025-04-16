package io.spring.oauth2.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Configuration properties for RSA keys.
 * <p>
 * This class is used to bind the RSA public and private keys from the application properties
 * file using the prefix "rsa".
 * See application-security.yml for configuration details.
 * </p>
 *
 * <pre>
 * Example configuration:
 * rsa.public-key=-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----
 * rsa.private-key=-----BEGIN PRIVATE KEY-----...-----END PRIVATE KEY-----
 * </pre>
 *
 * @param publicKey  the RSA public key
 * @param privateKey the RSA private key
 *
 * @since alpha 0.0.1
 */
@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}