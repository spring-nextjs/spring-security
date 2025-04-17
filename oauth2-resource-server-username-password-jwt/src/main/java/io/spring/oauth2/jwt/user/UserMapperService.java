package io.spring.oauth2.jwt.user;

import io.spring.oauth2.jwt.common.constant.Constant;
import io.spring.oauth2.jwt.role.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record UserMapperService(
        PasswordEncoder passwordEncoder,
        RoleService roleService
) {

    public UserDto mapToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
            );
    }

    public User mapCreateDtoToEntity(UserCreateDto userCreateDto) {
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRole(roleService.findRoleByName(Constant.Security.Role.ROLE_DEFAULT));
        return user;
    }

    public User mapUpdateDtoToEntity(User user, UserUpdateDto userUpdateDto) {
        user.setUsername(userUpdateDto.username());
        user.setEmail(userUpdateDto.email());
        user.setPassword(passwordEncoder.encode(userUpdateDto.password()));
        return user;
    }
}