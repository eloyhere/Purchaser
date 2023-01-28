package team.item.purchaser.forum.provider;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import team.item.purchaser.forum.entity.Consumer;
import team.item.purchaser.forum.service.ConsumerDetailsService;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AnonymousAuthenticationProvider implements AuthenticationProvider {

    private final HttpServletRequest request;

    private static final List<URI> whiteList = List.of(
            URI.create("/"), URI.create("/login"), URI.create("/register")
    );

    public AnonymousAuthenticationProvider(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        URI visit = URI.create(request.getRequestURI());
        Consumer anonymous = ConsumerDetailsService.anonymous();
        authentication.setAuthenticated(whiteList.contains(visit));
        return new AnonymousAuthenticationToken(anonymous.getUsername(), anonymous, anonymous.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Objects.equals(AnonymousAuthenticationToken.class, authentication);
    }
}