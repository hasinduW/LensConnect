package backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityconfig {
    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests (registry -> {
                    registry.anyRequest().authenticated();
                })
                .oauth2Login(oauth2login ->{
                    oauth2login
                            .loginPage("/login")
                            .successHandler((request, response, authentication) -> response.sendRedirect("/userInfo"));
                })
                .logout(logout ->
                        logout
                                .logoutUrl("/home")
                                .logoutSuccessUrl("/logout")
                                .permitAll()
                )
                .build();


        }

        }
