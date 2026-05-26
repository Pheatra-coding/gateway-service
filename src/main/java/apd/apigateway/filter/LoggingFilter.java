package apd.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_ID_HEADER = "X-Request-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String requestId = (String) exchange.getAttributes()
                .getOrDefault(REQUEST_ID_HEADER, "UNKNOWN");

        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange)
                .doOnTerminate(() -> {

                    // 🛑 prevent double execution
                    if (exchange.getAttributes().putIfAbsent("LOGGED", true) != null) {
                        return;
                    }

                    long duration = System.currentTimeMillis() - startTime;

                    Integer status = exchange.getResponse().getStatusCode() != null
                            ? exchange.getResponse().getStatusCode().value()
                            : 0;

                    log.info(
                            "[TRACE-ID={}] {} {} | status={} | duration={}ms",
                            requestId,
                            method,
                            path,
                            status,
                            duration
                    );
                });
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}