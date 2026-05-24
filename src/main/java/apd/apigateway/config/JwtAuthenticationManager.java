package apd.apigateway.config;

import apd.apigateway.exception.JwtAuthenticationException;
import apd.apigateway.security.KeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final KeyUtil keyUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return Mono.error(new JwtAuthenticationException("Invalid authentication type"));
        }

        String token = jwtAuth.getToken();

        // 1. Validate token only
        if (!keyUtil.validateToken(token)) {
            return Mono.error(new JwtAuthenticationException("Invalid or expired token"));
        }

        // 2. Extract data using KeyUtil (no direct Claims usage)
        String userId = keyUtil.extractUserId(token);
        String username = keyUtil.extractUsername(token);

        log.info("Authentication success | userId={} | username={}", userId, username);

        // 3. Build authenticated token (include scopes if needed)
        JwtAuthenticationToken authenticated =
                new JwtAuthenticationToken(
                        token,
                        userId,
                        username,
                        keyUtil.extractScopes(token)   // IMPORTANT for downstream
                );

        authenticated.setAuthenticated(true);

        return Mono.just(authenticated);
    }
}