package apd.apigateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<?> buildResponseEntity(HttpStatus status, String message) {
        return new ResponseEntity<>(
                Map.of(
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", message
                ),
                status
        );
    }

    /**
     * Spring Cloud Gateway - Route not found (endpoint missing)
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleGatewayNotFound(NotFoundException ex) {
        log.error("Gateway route not found: {}", ex.getMessage());
        return buildResponseEntity(
                HttpStatus.NOT_FOUND,
                "The requested endpoint does not exist in API Gateway."
        );
    }

    /**
     * ResponseStatusException - general Spring error wrapper
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        log.error("ResponseStatusException: {}", ex.getMessage());
        return buildResponseEntity(
                HttpStatus.valueOf(ex.getStatusCode().value()),
                ex.getReason() != null ? ex.getReason() : ex.getMessage()
        );
    }

    /**
     * Data integrity violation (duplicate key, constraint issues)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.CONFLICT, "Duplicate or invalid data.");
    }

    /**
     * Input validation failures
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return buildResponseEntity(HttpStatus.BAD_REQUEST, message);
    }

    /**
     NotFoundException
     */
    @ExceptionHandler(apd.apigateway.exception.NotFoundException.class)
    public ResponseEntity<?> handleCustomNotFound(apd.apigateway.exception.NotFoundException ex) {
        log.error("Custom NotFoundException: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Fallback for any unhandled exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred."
        );
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> handleServiceUnavailable(ServiceUnavailableException ex) {
        log.error("Service unavailable: {}", ex.getMessage());
        Map<String, Object> body = Map.of(
                "status", HttpStatus.SERVICE_UNAVAILABLE.value(),
                "error", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
                "message", ex.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }


}