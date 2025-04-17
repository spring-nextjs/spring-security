package io.spring.oauth2.jwt.user;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAllUsers();
    UserDto findUserById(UUID id);
    UserDto updateUserById(UUID id, UserUpdateDto userUpdateDto);
    void deleteUserById(UUID id);
    User findById(UUID id);
    User findByEmail(String email);
    User findByEmailOrUsername(String userNameOrEmail);
    User saveUser(User user);
}