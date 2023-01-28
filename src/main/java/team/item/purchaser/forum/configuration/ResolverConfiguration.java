package team.item.purchaser.forum.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.item.purchaser.forum.resolver.*;

import java.util.*;


@Configuration
public class ResolverConfiguration implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new EntityResolver());
        resolvers.add(new UUIDListResolver());
    }
}
