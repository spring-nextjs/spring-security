package io.spring.oauth2.jwt.user;

import io.spring.oauth2.jwt.common.payload.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
record UserServiceImpl(
        UserMapperService userMapperService,
        UserRepository userRepository
) implements UserService {

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapperService::mapToDto)
                .toList();
    }

    @Override
    public UserDto findUserById(UUID id) {
        return userMapperService.mapToDto(this.findById(id));
    }

    @Override
    public UserDto updateUserById(UUID id, UserUpdateDto userUpdateDto) {
        User user = this.findById(id);
        User updatedUser = userMapperService.mapUpdateDtoToEntity(user, userUpdateDto);
        return userMapperService.mapToDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.delete(this.findById(id));
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found.by.id", new String[]{id.toString()}));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found.by.email", new String[]{email}));
    }

    @Override
    public User findByEmailOrUsername(String emailOrUsername) {
        return userRepository.findByEmailOrUsername(emailOrUsername, emailOrUsername)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user.not.found.by.email.or.username",
                        new String[]{emailOrUsername})
                );
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}