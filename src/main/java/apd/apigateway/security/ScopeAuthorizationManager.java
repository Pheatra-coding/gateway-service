package apd.apigateway.security;

import apd.apigateway.config.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScopeAuthorizationManager
        implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final EndpointScopeRegistry registry;

    @Override
    public Mono<AuthorizationDecision> check(
            Mono<Authentication> authentication,
            AuthorizationContext context
    ) {

        String path = context.getExchange().getRequest().getPath().value();
        String method = context.getExchange().getRequest().getMethod().name();

        if (!registry.isEndpointRegistered(path)) {
            return authentication
                    .map(auth -> {
                        boolean isAuthenticated = auth.isAuthenticated();
                        return new AuthorizationDecision(isAuthenticated);
                    })
                    .defaultIfEmpty(new AuthorizationDecision(false));
        }

        String requiredScope = registry.getRequiredScope(path, method);
        if (requiredScope == null) {
            log.warn("No scope configured for {} {}", method, path);
            return Mono.just(new AuthorizationDecision(false));
        }

        return authentication
                .filter(Authentication::isAuthenticated)
                .filter(auth -> auth instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .map(auth -> {
                    boolean allowed = auth.hasScope(requiredScope);

                    return new AuthorizationDecision(allowed);
                })
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}