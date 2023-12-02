package ai.cvbird.cvbirdsite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

    // API

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("react/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/react/");
        registry.addResourceHandler("/*.json")
                .addResourceLocations("/react/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/react/");
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/react/index.html");
    }
}
