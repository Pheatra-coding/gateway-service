package apd.apigateway.filter;

import apd.apigateway.config.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
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

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(JwtAuthenticationToken.class::isInstance)
                .cast(JwtAuthenticationToken.class)
                .map(auth -> {

                    String userId = auth.getUserId();
                    String username = auth.getName();
                    String roles = auth.getAuthorities().stream()
                            .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(","));

                    log.info(
                            "Forwarding user info, path={}, username={}, userId={}, roles={}",
                            exchange.getRequest().getURI().getPath(),
                            username,
                            userId,
                            roles
                    );

                    return exchange.getRequest()
                            .mutate()
                            .header(USER_ID_HEADER, userId != null ? userId : "unknown")
                            .header(USERNAME_HEADER, username != null ? username : "unknown")
                            .header(USER_ROLES_HEADER, !roles.isEmpty() ? roles : "unknown")
                            .build();
                })
                .defaultIfEmpty(exchange.getRequest())
                .flatMap(request ->
                        chain.filter(exchange.mutate().request(request).build())
                );
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}
