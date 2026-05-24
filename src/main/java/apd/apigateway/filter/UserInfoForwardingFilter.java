package apd.apigateway.filter;

import apd.apigateway.config.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Component
public class UserInfoForwardingFilter implements GlobalFilter, Ordered {

    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USERNAME_HEADER = "X-Username";
    private static final String USER_ROLES_HEADER = "X-User-Roles";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Object authObj = exchange.getAttribute("auth");

        if (authObj instanceof JwtAuthenticationToken auth) {

            String userId = auth.getUserId();
            String username = auth.getName();

            String roles = auth.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            log.info("Forwarding user info | userId={} | username={}",
                    userId, username);

            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header(USER_ID_HEADER, userId != null ? userId : "unknown")
                    .header(USERNAME_HEADER, username != null ? username : "unknown")
                    .header(USER_ROLES_HEADER, roles.isEmpty() ? "unknown" : roles)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}