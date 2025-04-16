package io.spring.oauth2.jwt.auth;

import io.spring.oauth2.jwt.common.http.HttpRequestService;
import io.spring.oauth2.jwt.common.payload.exception.InvalidAuthenticationException;
import io.spring.oauth2.jwt.jwt.JwtService;
import io.spring.oauth2.jwt.token.TokenService;
import io.spring.oauth2.jwt.token.TokenType;
import io.spring.oauth2.jwt.user.User;
import io.spring.oauth2.jwt.user.UserCreateDto;
import io.spring.oauth2.jwt.user.UserMapperService;
import io.spring.oauth2.jwt.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthFactory authFactory;
    private final HttpRequestService httpRequestService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserMapperService userMapperService;
    private final UserService userService;

    /**
     * Authenticates a user based on the provided authentication request details.
     *
     * @param loginDto the authentication dto containing the email/username and password
     * @return an authentication response containing the JWT and the refresh token
     * @throws InvalidAuthenticationException if the provided email and password are invalid
     */
    @Override
    public AuthDto login(AuthLoginDto loginDto) {
        final String emailOrUsername = loginDto.getEmailOrUsername();
        User user = userService.findByEmailOrUsername(emailOrUsername);
        this.authenticateByUsernameAndPassword(emailOrUsername, loginDto.getPassword());
        tokenService.invalidateAllTokensByUserId(user.getId());
        String accessToken = jwtService.createJwt(user, TokenType.ACCESS);
        String refreshToken = jwtService.createJwt(user, TokenType.REFRESH);

        return authFactory.createAuthResponse(
                user, "auth.login.success", accessToken, refreshToken);
    }

    /**
     * Registers a new user based on the provided registration request details.
     *
     * @param userCreateDto the user creation dto containing the user's email, username and password
     * @return an authentication response containing the access token and the refresh token
     */
    @Override
    @Transactional
    public AuthDto register(UserCreateDto userCreateDto) {
        User user = userMapperService.mapCreateDtoToEntity(userCreateDto);
        User savedUser = userService.saveUser(user);
        String accessToken = jwtService.createJwt(user, TokenType.ACCESS);
        String refreshToken = jwtService.createJwt(user, TokenType.REFRESH);

        return authFactory.createAuthResponse(
                savedUser, "auth.register.success", accessToken, refreshToken);
    }

    /**
     * Authenticates a user based on the provided email and password via the
     * Authentication Manager provided by Spring Security.
     *
     * @param email    the email of the user to authenticate
     * @param password the password of the user to authenticate
     * @throws InvalidAuthenticationException if the provided email and password are invalid
     */
    private void authenticateByUsernameAndPassword(String email, String password) {
        try {
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(email, password);
            Authentication authentication = authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException ex) {
            throw new InvalidAuthenticationException("auth.invalid.credentials", new String[]{email});
        }
    }

    /**
     * Validates a user based on the provided jwt tokens.
     * If the jwt token is invalid but the refresh token is valid, a new jwt token is created using the refresh token.
     *
     * @return an authentication response containing the JWT and the refresh token
     * @throws InvalidAuthenticationException if the provided JWT and refresh token are invalid
     */
    @Override
    public AuthDto validate() {
        String accessToken = httpRequestService.extractAccessTokenFromAuthorizationHeader();
        String refreshToken = httpRequestService.extractRefreshTokenFromCookie();

        if (accessToken == null || refreshToken == null
                || !jwtService.isJwtValid(refreshToken, TokenType.REFRESH)) {
            throw new InvalidAuthenticationException("auth.invalid.credentials", new String[]{null});
        }

        String email = jwtService.extractEmailFromJwt(refreshToken, TokenType.REFRESH);
        User user = userService.findByEmail(email);

        if (!jwtService.isJwtValid(accessToken, TokenType.ACCESS)) {
            tokenService.invalidateAllTokensByUserId(user.getId());
            accessToken = jwtService.createJwt(user, TokenType.ACCESS);
            refreshToken = jwtService.createJwt(user, TokenType.REFRESH);
        }
        return authFactory.createAuthResponse(
                user, "auth.validate.success", accessToken, refreshToken);
    }
}