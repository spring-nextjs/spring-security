package io.spring.oauth2.jwt.role;

public interface RoleService {
    Role findRoleByName(String name);
}