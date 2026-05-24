package apd.apigateway.config;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final String userId;
    private final String username;
    private final List<String> scopes;

    public JwtAuthenticationToken(
            String token,
            String userId,
            String username
    ) {
        this(token, userId, username, Collections.emptyList());
    }

    public JwtAuthenticationToken(
            String token,
            String userId,
            String username,
            List<String> scopes
    ) {
        super(createAuthorities(scopes));

        this.token = token;
        this.userId = userId;
        this.username = username;
        this.scopes = scopes != null ? scopes : Collections.emptyList();

        // authentication state
        setAuthenticated(false);
    }

    private static Collection<? extends GrantedAuthority> createAuthorities(List<String> scopes) {
        if (scopes == null || scopes.isEmpty()) {
            return Collections.emptyList();
        }

        return scopes.stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}