package io.spring.oauth2.jwt.common.i18n;

import org.springframework.lang.Nullable;

/**
 * @since alpha 0.0.1
 * @see I18nServiceImpl
 */
public interface I18nService {
    String getLogMessage(String code);
    String getMessage(String code, @Nullable String... args);
}