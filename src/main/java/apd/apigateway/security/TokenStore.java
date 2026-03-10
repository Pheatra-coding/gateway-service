package apd.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenStore {

    private final ReactiveStringRedisTemplate redisTemplate;

    /**
     * Single-login validation:
     * Redis contains ONLY the latest token per user
     */
    public Mono<Boolean> isTokenValid(String userId, String token) {
        String key = "auth:access:" + userId;

        return redisTemplate.opsForValue()
                .get(key)
                .map(storedToken -> storedToken.equals(token))
                .defaultIfEmpty(false);
    }
}
