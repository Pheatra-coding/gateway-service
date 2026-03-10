package apd.apigateway.config;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class JwtAuthenticationToken implements Authentication {

    private final String token;
    private final String userId;
    private final String username;
    private final List<String> scopes;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated;

    public JwtAuthenticationToken(String token, String userId, String username) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.scopes = Collections.emptyList();
        this.authorities = Collections.emptyList();
        this.authenticated = false;
    }

    public JwtAuthenticationToken(String token, String userId, String username, List<String> scopes) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.scopes = scopes != null ? scopes : Collections.emptyList();
        this.authorities = createAuthorities(scopes);
        this.authenticated = true;
    }

    private Collection<? extends GrantedAuthority> createAuthorities(List<String> scopes) {
        if (scopes == null || scopes.isEmpty()) {
            return Collections.emptyList();
        }

        return scopes.stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }

    public boolean hasScope(String scope) {
        if (scopes == null || scopes.isEmpty() || scope == null) {
            return false;
        }

        boolean hasExactScope = scopes.contains(scope);

        boolean hasWildcardScope = scopes.stream()
                .anyMatch(userScope -> {
                    if (userScope.endsWith(":*")) {
                        String prefix = userScope.substring(0, userScope.length() - 2);

                        if (scope.startsWith(prefix + ":")) {
                            return true;
                        }

                        if (scope.equals(prefix)) {
                            return true;
                        }
                    }
                    return false;
                });

        boolean hasAdminScope = scopes.contains("*")
                || scopes.contains("admin")
                || scopes.contains("superuser");

        return hasExactScope || hasWildcardScope || hasAdminScope;
    }

    public boolean hasAnyScope(String... scopes) {
        for (String scope : scopes) {
            if (hasScope(scope)) {
                return true;
            }
        }
        return false;
    }

    public String getScopesAsString() {
        return String.join(", ", scopes);
    }
}
