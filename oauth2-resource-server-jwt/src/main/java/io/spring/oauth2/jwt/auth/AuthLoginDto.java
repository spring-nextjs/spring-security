package io.spring.oauth2.jwt.auth;

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
class AuthLoginDto {

  @NotBlank(message = EMAIL_OR_USERNAME_NOT_BLANK_ERROR)
  @Size(min = EMAIL_OR_USERNAME_MIN_CHAR, max = EMAIL_OR_USERNAME_MAX_CHAR, message = EMAIL_OR_USERNAME_SIZE_ERROR)
  @Pattern(regexp = EMAIL_OR_USERNAME_PATTERN, message = EMAIL_OR_USERNAME_PATTERN_ERROR)
  private String emailOrUsername;

  @NotBlank(message = PASSWORD_NOT_BLANK_ERROR)
  @Size(min = PASSWORD_MIN_CHAR, max = PASSWORD_MAX_CHAR, message = PASSWORD_SIZE_ERROR)
  @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_PATTERN_ERROR)
  private String password;
}