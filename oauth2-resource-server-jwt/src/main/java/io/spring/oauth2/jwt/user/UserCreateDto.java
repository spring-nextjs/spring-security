package io.spring.oauth2.jwt.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.spring.oauth2.jwt.common.constant.ValidationConstant.User.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

        @NotBlank(message = USERNAME_NOT_BLANK_ERROR)
        @Size(min = USERNAME_MIN_CHAR, max = USERNAME_MAX_CHAR, message = USERNAME_SIZE_ERROR)
        @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_ERROR)
        String username;

        @NotBlank(message = EMAIL_NOT_BLANK_ERROR)
        @Size(min = EMAIL_MIN_CHAR, max = EMAIL_MAX_CHAR, message = EMAIL_SIZE_ERROR)
        @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_PATTERN_ERROR)
        String email;

        @NotBlank(message = PASSWORD_NOT_BLANK_ERROR)
        @Size(min = PASSWORD_MIN_CHAR, max = PASSWORD_MAX_CHAR, message = PASSWORD_SIZE_ERROR)
        @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_PATTERN_ERROR)
        String password;
}