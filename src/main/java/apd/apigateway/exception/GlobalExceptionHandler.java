package apd.apigateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Configuration
@Order(-2)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        String correlationId = UUID.randomUUID().toString();

        log.error("Error occurred [CorrelationId: {}] - {}: {}",
                correlationId, throwable.getClass().getSimpleName(), throwable.getMessage());

        if (log.isDebugEnabled()) {
            log.debug("Stack trace: ", throwable);
        }

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().add("X-Correlation-Id", correlationId);

        HttpStatus status = determineHttpStatus(throwable);
        String message = determineMessage(throwable);

        response.setStatusCode(status);

        ErrorMessage errorMessage = new ErrorMessage(
                status.value(),
                new Date(),
                message,
                throwable.getClass().getSimpleName()
        );

        // Add correlation ID to error message
        errorMessage.setCorrelationId(correlationId);

        return writeResponse(response, errorMessage);
    }

    private HttpStatus determineHttpStatus(Throwable throwable) {
        if (throwable instanceof NotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (throwable instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) throwable;
            return HttpStatus.valueOf(rse.getStatusCode().value());
        } else if (throwable instanceof ServiceUnavailableException) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        } else if (throwable instanceof DataIntegrityViolationException) {
            return HttpStatus.CONFLICT;
        } else if (throwable instanceof MethodArgumentNotValidException) {
            return HttpStatus.BAD_REQUEST;
        } else if (throwable instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String determineMessage(Throwable throwable) {
        if (throwable instanceof NotFoundException) {
            return "The requested endpoint does not exist in API Gateway.";
        } else if (throwable instanceof ServiceUnavailableException) {
            return throwable.getMessage();
        } else if (throwable instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) throwable;
            return rse.getReason() != null ? rse.getReason() : "Service error occurred";
        } else if (throwable instanceof DataIntegrityViolationException) {
            return "Duplicate or invalid data.";
        } else if (throwable instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) throwable;
            if (ex.getBindingResult().getAllErrors().isEmpty()) {
                return "Validation failed";
            }
            return ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (throwable instanceof IllegalArgumentException) {
            return throwable.getMessage();
        }
        return "An unexpected error occurred. Please try again later.";
    }

    private Mono<Void> writeResponse(ServerHttpResponse response, ErrorMessage errorMessage) {
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(errorMessage);
                return bufferFactory.wrap(bytes);
            } catch (Exception e) {
                log.error("Failed to serialize error response", e);
                byte[] fallbackBytes = "{\"error\":\"Internal server error\"}".getBytes(StandardCharsets.UTF_8);
                return bufferFactory.wrap(fallbackBytes);
            }
        }));
    }
}