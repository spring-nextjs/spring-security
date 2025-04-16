package io.spring.oauth2.jwt.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.spring.oauth2.jwt.common.payload.response.OkResponse;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
class AuthDto extends OkResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonIgnore
  private String refreshToken;
}