package io.spring.oauth2.jwt.auth;

import io.spring.oauth2.jwt.common.constant.Constant;
import io.spring.oauth2.jwt.cookie.CookieService;
import io.spring.oauth2.jwt.user.UserCreateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.Request.AUTH)
record AuthController(
        AuthService authService,
        CookieService cookieService
) {

    @PostMapping("/login")
    ResponseEntity<AuthDto> login(@Valid @RequestBody AuthLoginDto loginDto) {
        AuthRequestNormalizer.normalize(loginDto);
        return createAuthResponse(authService.login(loginDto));
    }

    @PostMapping("/register")
    ResponseEntity<AuthDto> register(@Valid @RequestBody UserCreateDto userCreateDto) {
        AuthRequestNormalizer.normalize(userCreateDto);
        return createAuthResponse(authService.register(userCreateDto));
    }

    @GetMapping("/refresh")
    ResponseEntity<AuthDto> refresh() {
        return createAuthResponse(authService.refresh());
    }

    private ResponseEntity<AuthDto> createAuthResponse(AuthDto authDto) {
        ResponseCookie refreshTokenCookie = cookieService.generateRefreshTokenCookie(authDto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(authDto);
    }
}