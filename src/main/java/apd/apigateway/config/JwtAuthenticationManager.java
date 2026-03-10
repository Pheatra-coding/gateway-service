package apd.apigateway.config;

import apd.apigateway.exception.JwtAuthenticationException;
import apd.apigateway.security.KeyUtil;
import apd.apigateway.security.RedisTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final KeyUtil keyUtil;
    private final RedisTokenService redisTokenService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return Mono.error(new JwtAuthenticationException("Invalid authentication type"));
        }

        String token = jwtAuth.getToken();
        log.info("Authenticating JWT");

        if (!keyUtil.validateToken(token)) {
            return Mono.error(new JwtAuthenticationException("JWT validation failed"));
        }

        String username = keyUtil.extractUsername(token);
        if (username == null) {
            return Mono.error(new JwtAuthenticationException("Username not found in JWT"));
        }

        return redisTokenService.isTokenValid(username, token)
                .flatMap(valid -> {
                    if (!valid) {
                        return Mono.error(new JwtAuthenticationException("Token rejected by Redis (single-login)"));
                    }

                    String userId = keyUtil.extractUserId(token);
                    List<String> scopes = keyUtil.extractScopes(token);

                    JwtAuthenticationToken authenticated =
                            new JwtAuthenticationToken(
                                    token,
                                    userId != null ? userId : "unknown",
                                    username,
                                    scopes
                            );
                    authenticated.setAuthenticated(true);
                    return Mono.just(authenticated);
                });
    }
}
