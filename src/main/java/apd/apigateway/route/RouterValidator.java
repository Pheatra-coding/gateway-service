package apd.apigateway.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.List;

@Component
@Slf4j
public class RouterValidator {

    @Value("${api.ems_version}")
    private String apiVersion;

    public final Predicate<ServerHttpRequest> isSecured;

    public RouterValidator() {
        this.isSecured = request -> {
            String path = request.getURI().getPath();
            String basePath = "/api/" + apiVersion;

            List<String> openEndpoints = List.of(
                    basePath + "/auth/login",
                    basePath + "/auth/logout"
            );

            // Check if the current path matches any open endpoint
            boolean isPublic = openEndpoints.stream()
                    .anyMatch(endpoint -> {
                        if (endpoint.endsWith("/**")) {
                            String baseEndpoint = endpoint.substring(0, endpoint.length() - 3);
                            return path.startsWith(baseEndpoint);
                        } else {
                            return path.equals(endpoint) ||
                                    path.equals(endpoint + "/") ||
                                    path.startsWith(endpoint + "/");
                        }
                    });

            log.debug("Path: {}, isPublic: {}", path, isPublic);

            return !isPublic;
        };
    }
}