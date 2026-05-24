package apd.apigateway.config;

import apd.apigateway.exception.ErrorResponseWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationManager authenticationManager;
    private final JwtServerAuthenticationConverter authenticationConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http
    ) {

        return http

                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {})
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

                .addFilterAt(
                        jwtAuthenticationWebFilter(),
                        SecurityWebFiltersOrder.AUTHENTICATION
                )

                .authorizeExchange(exchange -> exchange

                        .pathMatchers(
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .pathMatchers(
                                "/api/**"
                        ).authenticated()

                        .anyExchange().permitAll()
                )

                .build();
    }

    private AuthenticationWebFilter jwtAuthenticationWebFilter() {

        AuthenticationWebFilter jwtFilter =
                new AuthenticationWebFilter(authenticationManager);

        jwtFilter.setServerAuthenticationConverter(
                authenticationConverter
        );

        jwtFilter.setAuthenticationFailureHandler(
                (webFilterExchange, exception) -> {

                    log.error(
                            "Authentication failed: {}",
                            exception.getMessage()
                    );

                    return ErrorResponseWriter.write(
                            webFilterExchange.getExchange(),
                            HttpStatus.UNAUTHORIZED,
                            "Invalid or expired access token"
                    );
                });

        return jwtFilter;
    }
}