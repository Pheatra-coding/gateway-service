package apd.apigateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ErrorResponseWriter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Mono<Void> write(
            ServerWebExchange exchange,
            HttpStatus status,
            String message
    ) {

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "timestamp", System.currentTimeMillis(),
                "status", status.value(),
                "error", status.name(),
                "message", message
        );

        try {
            byte[] bytes = mapper.writeValueAsBytes(body);

            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse()
                            .bufferFactory()
                            .wrap(bytes))
            );

        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }
}