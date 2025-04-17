package io.spring.oauth2.jwt.common.constant;

/**
 * Utility class that contains various HTTP-related constants used throughout the application.
 * This class cannot be instantiated.
 *
 * @since alpha 0.0.1
 */
public final class HttpConstant {

    /**
     * Private constructor to prevent instantiation.
     * Throws an UnsupportedOperationException if called.
     */
    HttpConstant() {
        throwUnsupportedOperationException();
    }

    /**
     * Nested class that contains constants related to HTTP headers.
     */
    public static final class Header {
        public static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";
        public static final String X_FORWARDED_FOR = "X-Forwarded-For";

        Header() {
            throwUnsupportedOperationException();
        }
    }

    /**
     * Nested class that contains constants related to HTTP body.
     */
    public static final class Body {
        public static final String UTF_8 = "UTF-8";

        Body() {
            throwUnsupportedOperationException();
        }
    }

    /**
     * Throws an UnsupportedOperationException to indicate that this class cannot be instantiated.
     */
    private static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}