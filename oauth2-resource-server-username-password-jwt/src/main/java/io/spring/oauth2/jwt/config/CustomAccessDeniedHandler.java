package io.spring.oauth2.jwt.config;

import com.nimbusds.jose.shaded.gson.Gson;
import io.spring.oauth2.jwt.common.constant.HttpConstant;
import io.spring.oauth2.jwt.common.i18n.I18nService;
import io.spring.oauth2.jwt.common.payload.response.UnauthorizedResponse;
import io.spring.oauth2.jwt.cookie.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * CustomAccessDeniedHandler is a Spring Security handler that manages access denied exceptions.
 * It customizes the response sent to the client when access denied exception occurs.
 * This handler is used within the {@link SecurityConfig} class to handle access denied exceptions.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link CookieService}: Service for managing cookies, used to clean access and refresh tokens.</li>
 *   <li>{@link I18nService}: Service for internationalization, used to retrieve localized messages.</li>
 *   <li>{@code @Value("${server.error.path}") String errorUri}: This value is injected from the application properties and represents the error path to be included in the error response.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Component}: Marks this class as a Spring component for autodetect during classpath scanning.</li>
 * </ul>
 *
 * @since alpha 0.0.1
 * @see AccessDeniedHandler
 * @see SecurityConfig
 */
@Component
record CustomAccessDeniedHandler(
        CookieService cookieService,
        I18nService i18nService,
        @Value("${server.error.path}") String errorPath
) implements AccessDeniedHandler {

    /**
     * Handles access denied exception by customizing the response sent to the client.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param accessDeniedException the AccessDeniedException thrown
     * @throws IOException if an input or output exception occurs
     */
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding(HttpConstant.Body.UTF_8);
        response.setHeader(HttpHeaders.AUTHORIZATION, null);
        response.addHeader(HttpHeaders.SET_COOKIE, cookieService.getCleanRefreshTokenCookie().toString());

        // Create response body
        UnauthorizedResponse unauthorizedResponse = new UnauthorizedResponse();
        unauthorizedResponse.setType(errorPath);
        unauthorizedResponse.setDetail(i18nService.getMessage("access.denied.unauthorized"));
        unauthorizedResponse.setInstance(request.getRequestURI());

        // Convert response body to JSON string
        Gson gson = new Gson();
        String serializedResponse = gson.toJson(unauthorizedResponse);

        // Write response body into the response using the writer
        response.getWriter().write(serializedResponse);
    }
}