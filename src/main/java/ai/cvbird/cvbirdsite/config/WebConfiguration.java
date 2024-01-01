package ai.cvbird.cvbirdsite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index.html");
        registry.addViewController("/signin").setViewName("/signin.html");
        registry.addViewController("/userpage").setViewName("/userpage.html");
        registry.addViewController("/signin_error").setViewName("/signin_error.html");
    }
}
