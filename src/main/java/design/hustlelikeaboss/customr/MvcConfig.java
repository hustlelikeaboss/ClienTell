package design.hustlelikeaboss.customr;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by quanjin on 7/19/17.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("security/home");
        registry.addViewController("/").setViewName("security/home");
        registry.addViewController("/hello").setViewName("security/hello");
        registry.addViewController("/login").setViewName("security/login");
    }

}
