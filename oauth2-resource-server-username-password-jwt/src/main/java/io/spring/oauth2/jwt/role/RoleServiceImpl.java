package io.spring.oauth2.jwt.role;

import io.spring.oauth2.jwt.common.payload.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public record RoleServiceImpl(
        RoleRepository roleRepository
) implements RoleService {

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("role.not.found.by.name", new String[]{name}));
    }
}