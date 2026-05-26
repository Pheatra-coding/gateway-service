package apd.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RateLimitLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        exchange.getResponse().beforeCommit(() -> {

            HttpStatus status =
                    (HttpStatus) exchange.getResponse().getStatusCode();

            if (status == HttpStatus.TOO_MANY_REQUESTS) {

                String requestId = exchange.getRequest()
                        .getHeaders()
                        .getFirst("X-Request-ID");

                String clientIp =
                        exchange.getRequest()
                                .getRemoteAddress() != null
                                ? exchange.getRequest()
                                .getRemoteAddress()
                                .getAddress()
                                .getHostAddress()
                                : "UNKNOWN";

                log.warn(
                        "Rate limit exceeded | requestId={} | IP={}",
                        requestId,
                        clientIp
                );
            }

            return Mono.empty();
        });

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}