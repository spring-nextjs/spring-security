package io.spring.oauth2.jwt.common.http;

import io.spring.oauth2.jwt.common.constant.CookieConstant;
import io.spring.oauth2.jwt.common.constant.HttpConstant;
import io.spring.oauth2.jwt.common.i18n.I18nService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * With each HTTP Servlet Request a diversity of headers are provided.
 * These headers contain data, such as the IP address of the client
 * or the principal (current logged-in user).
 * This class contains the methods to retrieve data from the headers.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link HttpServletRequest}: Represents an HTTP request and provides methods to access request parameters, headers, and attributes in a web application.</li>
 *   <li>{@link I18nService}: Service for internationalized messages.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Service}: Marks this class as a Spring service.</li>
 * </ul>
 *
 * @since alpha 0.0.1
 */
@Service
public record HttpRequestServiceImpl(
        HttpServletRequest httpServletRequest,
        I18nService i18nService
) implements HttpRequestService {

    /** Get the request URI using the Http Servlet Request.
     * @return request URI
     */
    @Override
    public String getRequestUri() {
        return httpServletRequest.getRequestURI();
    }

    /** Extract the client IP from the X-Forwarded-For Header using the Http Servlet Request.
     * @return client IP
     */
    @Override
    public String extractIpFromXffHeader() {
        final String xffHeader = httpServletRequest.getHeader(HttpConstant.Header.X_FORWARDED_FOR);
        if (xffHeader == null) {
            return httpServletRequest.getRemoteAddr();
        }
        return xffHeader.split(",")[0];
    }

    /** Extract the access token from the Authorization Header using the Http Servlet Request.
     * @return access token
     */
    @Override
    public String extractAccessTokenFromAuthorizationHeader() {
        final String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(HttpConstant.Header.AUTHORIZATION_TOKEN_PREFIX)) {
            return null;
        }
        return authHeader.substring(HttpConstant.Header.AUTHORIZATION_TOKEN_PREFIX.length());
    }

    /** Get the refresh token from the cookie using the Http Servlet Request.
     * @return refresh token
     */
    @Override
    public String extractRefreshTokenFromCookie() {
        final Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (CookieConstant.Header.REFRESH_TOKEN.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}