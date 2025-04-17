package io.spring.oauth2.jwt.common.constant;

/**
 * Utility class that contains various constants used throughout the application.
 * This class cannot be instantiated.
 *
 * @since alpha 0.0.1
 */
public final class Constant {

    private Constant() {
        // Private constructor to prevent instantiation
    }

    /**
     * Nested class that contains constants related to request paths.
     */
    public static final class Request {
        public static final String AUTH = "/auth";
        public static final String USERS = "/users";
        // add more constants as needed (in alphabetical order)

        Request() {
            throwUnsupportedOperationException();
        }
    }

    /**
     * Nested class that contains constants related to security.
     */
    public static final class Security {
        public static final class Role {
            public static final String ROLE_DEFAULT = "ROLE_USER";
            public static final String ROLE_ADMIN = "ROLE_ADMIN";
            // add more constants as needed (in alphabetical order)

            Role() {
                throwUnsupportedOperationException();
            }
        }

        public static final class Scope {
            public static final String SCOPE_USER_READ = "SCOPE_user:read";
            public static final String SCOPE_USER_CREATE = "SCOPE_user:create";
            public static final String SCOPE_USER_UPDATE = "SCOPE_user:update";
            public static final String SCOPE_USER_DELETE = "SCOPE_user:delete";
            public static final String SCOPE_MODERATOR_READ = "SCOPE_moderator:read";
            public static final String SCOPE_MODERATOR_CREATE = "SCOPE_moderator:create";
            public static final String SCOPE_MODERATOR_UPDATE = "SCOPE_moderator:update";
            public static final String SCOPE_MODERATOR_DELETE = "SCOPE_moderator:delete";
            public static final String SCOPE_ADMIN_READ = "SCOPE_admin:read";
            public static final String SCOPE_ADMIN_CREATE = "SCOPE_admin:create";
            public static final String SCOPE_ADMIN_UPDATE = "SCOPE_admin:update";
            public static final String SCOPE_ADMIN_DELETE = "SCOPE_admin:delete";
            // add more constants as needed (in alphabetical order)

            Scope() {
                throwUnsupportedOperationException();
            }
        }

        Security() {
            throwUnsupportedOperationException();
        }
    }

    /**
     * Nested class that contains constants related to database table names.
     */
    public static final class Table {
        public static final String JWT = "jwts";
        public static final String PERMISSIONS = "permissions";
        public static final String ROLES = "roles";
        public static final String TOKENS = "tokens";
        public static final String USERS = "users";
        // add more constants as needed (in alphabetical order)

        Table() {
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
