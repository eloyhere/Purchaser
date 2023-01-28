package team.item.purchaser.forum.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import team.item.purchaser.forum.filter.CrossOriginFilter;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<CrossOriginFilter> crossOrigin(){
        FilterRegistrationBean<CrossOriginFilter> bean = new FilterRegistrationBean<>();
        bean.addUrlPatterns("/*");
        bean.setFilter(new CrossOriginFilter());
        return bean;
    }
}
