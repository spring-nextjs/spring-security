package io.spring.oauth2.jwt.common.payload.exception;

import io.spring.oauth2.jwt.common.i18n.I18nService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler is a centralized exception handler for the application.
 * It handles specific exceptions and returns appropriate HTTP responses with
 * localized error messages.
 *
 * <p>This class is annotated with {@code @ControllerAdvice} to allow it to handle
 * exceptions across the whole application.</p>
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@code @Value("${server.error.path}") String errorPath}: This value is injected
 *   from the application properties and represents the error path to be included in the error response.</li>
 *   <li>{@link I18nService}: This service is used to retrieve localized messages.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @ControllerAdvice}: Marks this class as a global exception handler.</li>
 *   <li>{@code @RequiredArgsConstructor}: Generates a constructor with required arguments.</li>
 * </ul>
 *
 * @since alpha 0.0.1
 */
@ControllerAdvice
@RequiredArgsConstructor
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Setter
    @Value("${server.error.path}")
    private String errorPath;

    private final I18nService i18nService;

    /**
     * Handles ResourceNotFoundException and returns a ProblemDetail object
     * with HTTP status 404 (Not Found) and a localized error message.
     *
     * @param ex the ResourceNotFoundException thrown when a resource is not found
     * @return ProblemDetail object containing the error details and HTTP status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        return getProblemDetail(
                HttpStatus.NOT_FOUND,
                i18nService.getMessage(
                        ex.getMessage(),
                        ex.getArgs()
                )
        );
    }

    /**
     * Handles ResourceAlreadyExistsException and returns a ProblemDetail object
     * with HTTP status 409 (Conflict) and a localized error message.
     *
     * @param ex the ResourceAlreadyExistsException thrown when a resource already exists
     * @return ProblemDetail object containing the error details and HTTP status
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    ProblemDetail handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return getProblemDetail(
                HttpStatus.CONFLICT,
                i18nService.getMessage(
                        ex.getMessage(),
                        ex.getArgs()
                )
        );
    }

    /**
     * Handles InvalidAuthenticationException and returns a ProblemDetail object
     * with HTTP status 401 (Unauthorized) and a localized error message.
     *
     * @param ex the InvalidAuthenticationException thrown when invalid authentication details are provided
     * @return ProblemDetail object containing the error details and HTTP status
     */
    @ExceptionHandler(InvalidAuthenticationException.class)
    ProblemDetail handleInvalidAuthenticationException(InvalidAuthenticationException ex) {
        return getProblemDetail(
                HttpStatus.UNAUTHORIZED,
                i18nService.getMessage(
                        ex.getMessage(),
                        ex.getArgs()
                )
        );
    }

    /**
     * Handles {@link MethodArgumentNotValidException} exceptions thrown when method arguments annotated with validation
     * constraints are invalid.
     *
     * <p>This method constructs a detailed error message by concatenating all the validation error messages for each
     * invalid field. It then returns a {@link ResponseEntity} containing a {@code ProblemDetail} object with the
     * constructed error message and an HTTP 400 Bad Request status.
     *
     * @param ex the exception that indicates that a method argument is not valid
     * @param headers the HTTP headers that were part of the request
     * @param status the HTTP status code
     * @param request the web request during which the exception was thrown
     * @return a {@link ResponseEntity} object containing a {@code ProblemDetail} object with a detailed error message
     *         and an HTTP 400 Bad Request status
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ProblemDetail pd = ex.getBody();
        pd.setType(URI.create(errorPath));
        ex.getBindingResult().getAllErrors().forEach(error ->
                validationErrors.put(((FieldError) error).getField(), error.getDefaultMessage()));

        pd.setDetail(validationErrors.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(";")));

        return ResponseEntity.badRequest().body(pd);
    }

    /**
     * Constructs a ProblemDetail object with the specified HTTP status and detail message.
     * Sets the type of the problem detail to the configured error URI.
     *
     * @param httpStatus the HTTP status to set for the ProblemDetail
     * @param detail the detail message to set for the ProblemDetail
     * @return ProblemDetail object with the specified status and detail message
     */
    private ProblemDetail getProblemDetail(HttpStatus httpStatus, String detail) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                httpStatus,
                detail
        );
        pd.setType(URI.create(errorPath));
        return pd;
    }
}