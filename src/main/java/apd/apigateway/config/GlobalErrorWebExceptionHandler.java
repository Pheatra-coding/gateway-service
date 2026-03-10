package apd.apigateway.config;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Add GlobalError Web-Exception
 */
@Component
@Order(-1)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        if (ex.getMessage() != null && ex.getMessage().contains("404")) {
            status = HttpStatus.NOT_FOUND;
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorMessage = String.format(
                "{\"error\": \"%s\", \"message\": \"%s\", \"code\": %d}",
                status.getReasonPhrase(),
                ex.getMessage() != null ? ex.getMessage().replace("\"", "\\\"") : "Unknown error",
                status.value()
        );

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(errorMessage.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}