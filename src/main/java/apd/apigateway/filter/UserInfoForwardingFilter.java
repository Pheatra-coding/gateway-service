package apd.apigateway.filter;

import apd.apigateway.config.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserInfoForwardingFilter implements GlobalFilter, Ordered {

    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USERNAME_HEADER = "X-Username";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(JwtAuthenticationToken.class::isInstance)
                .cast(JwtAuthenticationToken.class)
                .flatMap(auth -> {

                    String userId = auth.getUserId();
                    String username = auth.getName();

                    ServerHttpRequest.Builder requestBuilder =
                            exchange.getRequest().mutate();

                    if (userId != null && !userId.isBlank()) {
                        requestBuilder.header(USER_ID_HEADER, userId);
                    }

                    if (username != null && !username.isBlank()) {
                        requestBuilder.header(USERNAME_HEADER, username);
                    }

                    log.info(
                            "Forwarding user context | userId={} | username={}",
                            userId,
                            username
                    );

                    return chain.filter(
                            exchange.mutate()
                                    .request(requestBuilder.build())
                                    .build()
                    );
                })
                .switchIfEmpty(Mono.defer(() -> chain.filter(exchange)));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}


//package apd.apigateway.filter;
//
//import apd.apigateway.config.JwtAuthenticationToken;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//public class UserInfoForwardingFilter implements GlobalFilter, Ordered {
//
//    private static final String USER_ID_HEADER = "X-User-Id";
//    private static final String USERNAME_HEADER = "X-Username";
//    private static final String USER_ROLES_HEADER = "X-User-Roles";
//    private static final String USER_SCOPES_HEADER = "X-User-Scopes";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        Object authObj = exchange.getAttribute("auth");
//
//        if (!(authObj instanceof JwtAuthenticationToken auth)) {
//            return chain.filter(exchange);
//        }
//
//        String userId = auth.getUserId();
//        String username = auth.getName();
//
//        String roles = auth.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .filter(authority -> authority.startsWith("ROLE_"))
//                .collect(Collectors.joining(","));
//
//        String scopes = auth.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .filter(authority -> !authority.startsWith("ROLE_"))
//                .collect(Collectors.joining(","));
//
//        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
//
//        if (userId != null && !userId.isBlank()) {
//            builder.header(USER_ID_HEADER, userId);
//        }
//
//        if (username != null && !username.isBlank()) {
//            builder.header(USERNAME_HEADER, username);
//        }
//
//        if (!roles.isBlank()) {
//            builder.header(USER_ROLES_HEADER, roles);
//        }
//
//        if (!scopes.isBlank()) {
//            builder.header(USER_SCOPES_HEADER, scopes);
//        }
//
//        log.info(
//                "Forwarding user info | userId={} | username={} | roles={} | scopes={}",
//                userId,
//                username,
//                roles,
//                scopes
//        );
//
//        return chain.filter(
//                exchange.mutate()
//                        .request(builder.build())
//                        .build()
//        );
//    }
//
//    @Override
//    public int getOrder() {
//        return Ordered.LOWEST_PRECEDENCE - 100;
//    }
//}