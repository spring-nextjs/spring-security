package io.spring.oauth2.jwt.common.constant;

/**
 * Utility class that contains various Cookie-related constants used throughout the application.
 * This class cannot be instantiated.
 *
 * @since alpha 0.0.1
 */
public class CookieConstant {

    /**
     * Private constructor to prevent instantiation.
     * Throws an UnsupportedOperationException if called.
     */
    CookieConstant() {
        throwUnsupportedOperationException();
    }

    /**
     * Nested class that contains constants related to Cookie headers.
     */
    public static final class Header {
        public static final String REFRESH_TOKEN = "refresh_token";

        Header() {
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