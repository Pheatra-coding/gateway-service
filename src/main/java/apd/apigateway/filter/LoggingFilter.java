package apd.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter {

    private static final String HEADER_NAME = "X-Request-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String requestId = exchange.getRequest()
                .getHeaders()
                .getFirst(HEADER_NAME);

        if (requestId == null) {
            Object attr = exchange.getAttributes().get(HEADER_NAME);
            requestId = attr != null ? attr.toString() : "UNKNOWN";
        }

        final String finalRequestId = requestId;

        return chain.filter(exchange)
                .doFinally(signal -> {

                    long duration = System.currentTimeMillis() - startTime;
                    HttpStatusCode status = exchange.getResponse().getStatusCode();

                    log.info("[{}] {} {} | Status: {} | {} ms",
                            finalRequestId,
                            exchange.getRequest().getMethod(),
                            exchange.getRequest().getURI().getPath(),
                            status != null ? status.value() : "UNKNOWN",
                            duration);
                });
    }
}