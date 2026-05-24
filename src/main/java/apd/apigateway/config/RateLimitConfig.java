package apd.apigateway.config;

import apd.apigateway.security.KeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RateLimitConfig {

    private final KeyUtil keyUtil;

    @Bean
    public KeyResolver userKeyResolver() {

        return exchange -> Mono.fromSupplier(() -> {

            try {

                String authHeader = exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader != null &&
                        authHeader.startsWith("Bearer ")) {

                    String token = authHeader.substring(7);

                    String userId = keyUtil.extractUserId(token);

                    if (userId != null && !userId.isBlank()) {
                        return "USER:" + userId;
                    }
                }

            } catch (Exception ex) {
                log.debug("Rate limit fallback to IP");
            }

            return "IP:" + getClientIp(exchange);
        });
    }

    private String getClientIp(ServerWebExchange exchange) {

        String xForwardedFor = exchange.getRequest()
                .getHeaders()
                .getFirst("X-Forwarded-For");

        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }

        InetSocketAddress remoteAddress =
                exchange.getRequest().getRemoteAddress();

        if (remoteAddress != null &&
                remoteAddress.getAddress() != null) {

            return remoteAddress.getAddress().getHostAddress();
        }

        return "UNKNOWN";
    }
}