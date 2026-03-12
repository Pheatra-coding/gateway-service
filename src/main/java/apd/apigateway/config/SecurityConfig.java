package apd.apigateway.config;

import apd.apigateway.constant.Endpoint;
import apd.apigateway.exception.ErrorResponseWriter;
import apd.apigateway.security.ScopeAuthorizationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import static apd.apigateway.constant.Endpoint.*;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Value("/api/${api.ems_version}")
    private String eLearningApiVersion;

    private final JwtAuthenticationManager authenticationManager;
    private final JwtServerAuthenticationConverter authenticationConverter;
    private final ScopeAuthorizationManager scopeAuthorizationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        log.info("Initializing API Gateway security configuration");

        return http
                /* ---------- DISABLE DEFAULT SECURITY ---------- */
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {}) // respects global CORS config
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

                /* ---------- JWT AUTH FILTER ---------- */
                .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)

                /* ---------- AUTHORIZATION RULES ---------- */

                .build();
    }

    private AuthenticationWebFilter jwtAuthenticationWebFilter() {
        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(authenticationManager);
        jwtFilter.setServerAuthenticationConverter(authenticationConverter);

        jwtFilter.setAuthenticationFailureHandler((webFilterExchange, exception) -> {
            log.info("Authentication failed: {}", exception.getMessage());
            return ErrorResponseWriter.write(
                    webFilterExchange.getExchange(),
                    HttpStatus.UNAUTHORIZED,
                    "Invalid or expired access token"
            );
        });

        return jwtFilter;
    }

    private String[] getPublicEndpoints() {
        return new String[]{
                Endpoint.ACTUATOR,
                Endpoint.HEALTH,
                Endpoint.INFO
        };
    }

    private String[] getSwaggerEndpoints() {
        return new String[]{
                Endpoint.SWAGGER_UI,
                Endpoint.SWAGGER_UI_HTML,
                Endpoint.API_DOCS,
                Endpoint.SWAGGER_RESOURCES,
                Endpoint.WEBJARS
        };
    }
}