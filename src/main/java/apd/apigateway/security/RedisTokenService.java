package apd.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final ReactiveStringRedisTemplate redisTemplate;

    /**
     * Get token from Redis by key
     * Example key: user_token:username
     */
    public Mono<String> get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Validate if provided token matches Redis stored token
     */
    public Mono<Boolean> isTokenValid(String username, String token) {
        String redisKey = "user_token:" + username;

        return get(redisKey)
                .map(storedToken -> {
                    String cleanStoredToken = storedToken.replace("\"", "");
                    return cleanStoredToken.equals(token);
                })
                .defaultIfEmpty(false);
    }
}
