package team.item.purchaser.forum.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import team.item.purchaser.forum.entity.Consumer;
import team.item.purchaser.forum.service.ConsumerDetailsService;
import team.item.purchaser.forum.service.ConsumerService;

import java.util.Objects;
import java.util.Optional;

@Component(value = "UsernamePasswordAuthenticationProvider")
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ConsumerDetailsService consumerDetailsService;

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Consumer consumer = new Consumer();
        consumer.setUsername(authentication.getName());
        consumer.setPassword((String) authentication.getCredentials());
        return consumerDetailsService.login(consumer)?
                UsernamePasswordAuthenticationToken.authenticated(authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities()):
                UsernamePasswordAuthenticationToken.unauthenticated(authentication.getPrincipal(), authentication.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Objects.equals(UsernamePasswordAuthenticationToken.class, authentication);
    }
}
