// src/main/java/com/example/socialapp/config/SecurityConfig.java
package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// import org.springframework.security.core.userdetails.UserModel;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())

                                // <— disable Spring Security's X-Frame-Options header so H2 console can use
                                // frames
                                .headers(headers -> headers.frameOptions().disable())

                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().permitAll());
                return http.build();
        }
        // @Bean
        // UserDetailsService users() {
        // var uds = new InMemoryUserDetailsManager();
        // uds.createUser(
        // UserModel.withDefaultPasswordEncoder()
        // .username("alice")
        // .password("password")
        // .roles("USER")
        // .build());
        // return uds;
        // }
}
