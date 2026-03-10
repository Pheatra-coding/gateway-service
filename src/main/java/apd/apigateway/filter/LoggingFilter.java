package apd.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter {

    private static final String HEADER_NAME = "X-Request-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        // Get request ID from headers or attributes
        String requestIdTemp = exchange.getRequest().getHeaders().getFirst(HEADER_NAME);
        if (requestIdTemp == null) {
            Object attr = exchange.getAttributes().get(HEADER_NAME);
            requestIdTemp = (attr != null) ? attr.toString() : "UNKNOWN";
        }
        final String requestId = requestIdTemp; // make final for lambda

//        return chain.filter(exchange).then(
//                Mono.fromRunnable(() -> {
//                    long duration = System.currentTimeMillis() - startTime;
//                    HttpStatusCode status = exchange.getResponse().getStatusCode();
//
//                    log.info(
//                            "[{}] Request: {} {} | Status: {} | Time: {} ms",
//                            requestId,
//                            exchange.getRequest().getMethod(),
//                            exchange.getRequest().getURI().getPath(),
//                            status != null ? status.value() : "UNKNOWN",
//                            duration
//                    );
//                })
//        );

        //TODO
        return chain.filter(exchange)
                .doFinally(signal -> {
                    long duration = System.currentTimeMillis() - startTime;
                    HttpStatusCode status = exchange.getResponse().getStatusCode();

                    log.info("[{}] {} {} | Status: {} | {} ms",
                            requestId,
                            exchange.getRequest().getMethod(),
                            exchange.getRequest().getURI().getPath(),
                            status != null ? status.value() : "UNKNOWN",
                            duration);
                });
    }
}
