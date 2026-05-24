package apd.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class KeyUtil {

    @Value("${app.token.jwtKey}")
    private String jwtKey;

    /**
     * Build signing key (SAFE production version)
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Extract all claims safely
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Generic claim extractor
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    // =========================
    // USER IDENTIFICATION
    // =========================

    public String extractUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);

            return firstNonNull(
                    claims.get("user_name"),
                    claims.get("username"),
                    claims.get("preferred_username"),
                    claims.get("sub")
            );
        } catch (Exception e) {
            log.warn("Failed to extract username from JWT");
            return "unknown";
        }
    }

    public String extractUserId(String token) {
        try {
            Claims claims = extractAllClaims(token);

            return firstNonNull(
                    claims.get("user_id"),
                    claims.get("id"),
                    claims.getSubject()
            );
        } catch (Exception e) {
            log.warn("Failed to extract userId from JWT");
            return "unknown";
        }
    }

    // =========================
    // SCOPES / ROLES
    // =========================

    public List<String> extractScopes(String token) {
        try {
            Claims claims = extractAllClaims(token);

            Object scope = claims.get("scope");
            if (scope == null) return List.of();

            if (scope instanceof List<?> list) {
                return list.stream().map(Object::toString).toList();
            }

            if (scope instanceof String str) {
                return List.of(str.split("\\s+"));
            }

            return List.of();
        } catch (Exception e) {
            log.warn("Failed to extract scopes from JWT");
            return List.of();
        }
    }

    // =========================
    // TOKEN VALIDATION
    // =========================

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);

            Date expiration = claims.getExpiration();
            if (expiration == null) {
                return false;
            }

            return expiration.after(new Date());

        } catch (Exception e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    // =========================
    // UTIL
    // =========================

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String maskToken(String token) {
        if (token == null || token.length() < 20) {
            return "INVALID_TOKEN";
        }
        return token.substring(0, 10) + "..." + token.substring(token.length() - 6);
    }

    /**
     * Safe helper
     */
    private String firstNonNull(Object... values) {
        for (Object v : values) {
            if (v != null && !v.toString().isBlank()) {
                return v.toString();
            }
        }
        return "unknown";
    }
}