package apd.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Configuration
public class CorsConfig {

    /**
     * Dynamic CORS filter for SIT testing.
     * Allows any frontend running on testers' machines to call the SIT API.
     */
    @Bean
    public CorsWebFilter corsWebFilter() {

        // base configuration
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        // dynamic source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
                CorsConfiguration cors = new CorsConfiguration();
                cors.setAllowedMethods(config.getAllowedMethods());
                cors.setAllowedHeaders(config.getAllowedHeaders());
                cors.setAllowCredentials(config.getAllowCredentials());

                // allow the request origin dynamically
                String origin = exchange.getRequest().getHeaders().getOrigin();
                if (origin != null && !origin.isEmpty()) {
                    cors.setAllowedOrigins(List.of(origin));
                }

                return cors;
            }
        };

        // apply to all paths
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}