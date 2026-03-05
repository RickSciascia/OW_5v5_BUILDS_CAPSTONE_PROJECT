package ricksciascia.ow_5v5_build.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.formLogin(fl-> fl.disable());
        httpSecurity.csrf(csrf->csrf.disable());
        httpSecurity.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(r->r.requestMatchers("/**").permitAll());


        return httpSecurity.build();
    }
}
