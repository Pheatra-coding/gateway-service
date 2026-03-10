package apd.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
public class RateLimitConfig {

    private static final String UNKNOWN = "unknown";
    private static final String FORWARDED_HEADER = "X-Forwarded-For";

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.fromSupplier(() -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();

            String clientIp = resolveClientIp(headers, remoteAddress);
            log.debug("Resolved client IP: {}", clientIp);
            return clientIp;
        });
    }

    /**
     * Resolves client IP using X-Forwarded-For headers and remote address.
     * Takes the last public IP from X-Forwarded-For to prevent header spoofing.
     */
    private String resolveClientIp(HttpHeaders headers, InetSocketAddress remoteAddress) {
        String forwardedFor = headers.getFirst(FORWARDED_HEADER);
        if (StringUtils.hasText(forwardedFor)) {
            List<String> ips = Arrays.asList(forwardedFor.split(","));
            Collections.reverse(ips);

            for (String ip : ips) {
                String trimmedIp = ip.trim();
                if (isPublicIp(trimmedIp)) {
                    return trimmedIp;
                }
            }
            log.warn("No valid public IP found in X-Forwarded-For header: {}", forwardedFor);
        }

        // Fallback to remote address
        if (remoteAddress != null && remoteAddress.getAddress() != null) {
            String ip = remoteAddress.getAddress().getHostAddress();
            if (isPublicIp(ip)) return ip;
        }

        // Final fallback
        log.warn("Could not resolve client IP, using fallback: {}", UNKNOWN);
        return UNKNOWN;
    }

    /**
     * Returns true if the IP is well-formed and public (IPv4 or IPv6)
     */
    private boolean isPublicIp(String ip) {
        if (!StringUtils.hasText(ip)) return false;

        try {
            InetAddress inet = InetAddress.getByName(ip);
            return !inet.isAnyLocalAddress() &&
                    !inet.isLoopbackAddress() &&
                    !inet.isSiteLocalAddress() &&
                    !inet.isLinkLocalAddress() &&
                    !inet.isMulticastAddress();
        } catch (Exception e) {
            log.warn("Invalid IP format: {}", ip, e);
            return false;
        }
    }
}