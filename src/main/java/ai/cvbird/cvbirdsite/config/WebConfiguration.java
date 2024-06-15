package ai.cvbird.cvbirdsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.html");
        registry.addViewController("/signin").setViewName("signin.html");
        registry.addViewController("/userpage").setViewName("userpage.html");
        registry.addViewController("/signin_error").setViewName("signin_error.html");
        registry.addViewController("/user_registration").setViewName("user_registration.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/swagger/**")
                .allowedOrigins("http://localhost:8089", "http://cvbird.ai:8089")
                .allowedMethods("GET");
    }
}
