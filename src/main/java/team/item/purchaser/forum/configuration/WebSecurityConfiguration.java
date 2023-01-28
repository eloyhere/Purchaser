package team.item.purchaser.forum.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import team.item.purchaser.forum.provider.AnonymousAuthenticationProvider;
import team.item.purchaser.forum.provider.UsernamePasswordAuthenticationProvider;
import team.item.purchaser.forum.service.*;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    ConsumerDetailsService consumerDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider(){
        return new UsernamePasswordAuthenticationProvider(consumerDetailsService);
    }

    @Bean
    public AnonymousAuthenticationProvider anonymousAuthenticationProvider(){
        return new AnonymousAuthenticationProvider(request);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity security) throws Exception {
        return security
                .formLogin().permitAll()
                .and().authorizeHttpRequests().anyRequest().authenticated()
                .and().rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(600)
                .userDetailsService(consumerDetailsService)
                .tokenRepository(tokenService)
                .and().cors().and().csrf().disable().build();
    }
}