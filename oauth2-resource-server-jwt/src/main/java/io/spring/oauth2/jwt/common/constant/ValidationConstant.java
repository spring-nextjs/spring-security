package io.spring.oauth2.jwt.common.constant;

public final class ValidationConstant {

    /**
     * Private constructor to prevent instantiation.
     * Throws an UnsupportedOperationException if called.
     */
    ValidationConstant() {
        throwUnsupportedOperationException();
    }

    /**
     * Nested class that contains constants related to User validation.
     */
    public static final class User {
        public static final int EMAIL_OR_USERNAME_MIN_CHAR = 3;
        public static final int EMAIL_OR_USERNAME_MAX_CHAR = 320;
        public static final String EMAIL_OR_USERNAME_PATTERN = "^[^=*]+$";

        public static final int USERNAME_MIN_CHAR = 3;
        public static final int USERNAME_MAX_CHAR = 50;
        public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]+$";

        public static final int EMAIL_MIN_CHAR = 8;
        public static final int EMAIL_MAX_CHAR = 320;
        public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        public static final int PASSWORD_MIN_CHAR = 8;
        public static final int PASSWORD_MAX_CHAR = 256;
        // Password must consist of at least  8 characters, one lower- and one uppercase alphabetical letter, one numeric number and one special character !@#$%^&_
        public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&]).{8,}$";

        public static final String EMAIL_OR_USERNAME_NOT_BLANK_ERROR = "{validation.user.emailOrUsername.NotBlank}";
        public static final String EMAIL_OR_USERNAME_SIZE_ERROR = "{validation.user.emailOrUsername.Size}";
        public static final String EMAIL_OR_USERNAME_PATTERN_ERROR = "{validation.user.emailOrUsername.Pattern}";

        public static final String USERNAME_NOT_BLANK_ERROR = "{validation.user.username.NotBlank}";
        public static final String USERNAME_SIZE_ERROR = "{validation.user.username.Size}";
        public static final String USERNAME_PATTERN_ERROR = "{validation.user.username.Pattern}";

        public static final String EMAIL_NOT_BLANK_ERROR = "{validation.user.email.NotBlank}";
        public static final String EMAIL_SIZE_ERROR = "{validation.user.email.Size}";
        public static final String EMAIL_PATTERN_ERROR = "{validation.user.email.Pattern}";

        public static final String PASSWORD_NOT_BLANK_ERROR = "{validation.user.password.NotBlank}";
        public static final String PASSWORD_SIZE_ERROR = "{validation.user.password.Size}";
        public static final String PASSWORD_PATTERN_ERROR = "{validation.user.password.Pattern}";

        User() {
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
