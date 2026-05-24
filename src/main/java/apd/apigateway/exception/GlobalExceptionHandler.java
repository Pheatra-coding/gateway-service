package apd.apigateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<?> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", message
                )
        );
    }

    // =========================
    // GATEWAY ROUTE NOT FOUND
    // =========================
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleGatewayNotFound(NotFoundException ex) {
        log.error("Gateway route not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Requested API route not found in Gateway"
        );
    }

    // =========================
    // SPRING STATUS EXCEPTION
    // =========================
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {
        log.error("ResponseStatusException: {}", ex.getMessage());

        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

        return buildResponse(
                status,
                ex.getReason() != null ? ex.getReason() : ex.getMessage()
        );
    }

    // =========================
    // VALIDATION ERROR
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        log.error("Validation error: {}", message);

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    // =========================
    // DATA CONFLICT
    // =========================
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataConflict(Exception ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, "Data conflict or duplicate entry");
    }

    // =========================
    // CUSTOM NOT FOUND
    // =========================
    @ExceptionHandler(apd.apigateway.exception.NotFoundException.class)
    public ResponseEntity<?> handleCustomNotFound(apd.apigateway.exception.NotFoundException ex) {
        log.error("Custom NotFoundException: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // =========================
    // SERVICE UNAVAILABLE (FALLBACK)
    // =========================
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> handleServiceUnavailable(ServiceUnavailableException ex) {
        log.error("Service unavailable: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                ex.getMessage()
        );
    }

    // =========================
    // GLOBAL FALLBACK
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        log.error("Unexpected error: ", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error occurred"
        );
    }
}