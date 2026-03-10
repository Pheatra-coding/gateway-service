package apd.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class KeyUtil {

    @Value("${app.token.jwtKey}")
    private String jwtKey;

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtKey.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            byte[] paddedKey = new byte[32];
            System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
            return Keys.hmacShaKeyFor(paddedKey);
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<String> extractScopes(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Object scope = claims.get("scope");

            if (scope instanceof List) {
                return (List<String>) scope;
            } else if (scope instanceof String) {
                String scopeString = (String) scope;
                return List.of(scopeString.split("\\s+"));
            }

            log.trace("No scopes found in JWT token");
            return List.of();
        } catch (Exception e) {
            log.error("Error extracting scopes from token: {}", e.getMessage());
            return List.of();
        }
    }

    public List<String> extractAllAuthorities(String token) {
        return new ArrayList<>(extractScopes(token));
    }

    public String extractUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Object userName = claims.get("user_name");
            if (userName != null) {
                return userName.toString();
            }

            Object sub = claims.get("sub");
            if (sub != null) {
                return sub.toString();
            }

            log.warn("No username found in token");
            return "unknown";
        } catch (Exception e) {
            log.error("Error extracting username from token: {}", e.getMessage());
            return "unknown";
        }
    }

    public String extractUserId(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Object id = claims.get("id");
            if (id != null) {
                return id.toString();
            }

            Object userId = claims.get("user_id");
            if (userId != null) {
                return userId.toString();
            }

            log.warn("User ID not found in JWT token");
            return "unknown";
        } catch (Exception e) {
            log.error("Error extracting user ID from token: {}", e.getMessage());
            return "unknown";
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Failed to extract claims from token: {}", e.getMessage());
            throw e;
        }
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            Date expiration = extractExpiration(token);
            if (expiration.before(new Date())) {
                log.warn("Token has expired");
                return false;
            }

            return true;

        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}