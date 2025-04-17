package io.spring.oauth2.jwt.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnauthorizedResponse {

    private String type;
    private String title = HttpStatus.UNAUTHORIZED.getReasonPhrase();
    private int status = HttpStatus.UNAUTHORIZED.value();
    private String detail;
    private String instance;
}