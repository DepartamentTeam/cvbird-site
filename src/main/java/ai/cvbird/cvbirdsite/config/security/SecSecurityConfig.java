package ai.cvbird.cvbirdsite.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {
    @Autowired
    UrlAuthenticationSuccessHandler urlAuthenticationSuccessHandler;

    @Autowired
    AwareHandler awareHandler;

   @Bean
   public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests((authorize) ->
                       authorize
                               //.requestMatchers( "/login**", "/icons/**", "/_next/**", "/manifest.json", "/img/**", "/static/**").permitAll()
                               .requestMatchers( "/signin**", "/", "/_next/**", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
                               .anyRequest().authenticated()
              ).formLogin(
                      form -> form
                              .loginPage("/signin")
                              .loginProcessingUrl("/signin")
                              .successHandler(awareHandler)
                              .failureUrl("/signin?error=true")
                              //.failureUrl("/error")
                              .permitAll()
              ).logout(
                      logout -> logout
                              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                              .permitAll()
              );
       return http.build();
   }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user= User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
