package com.kohhx.Spring.Security.Basic.Auth.MySQL.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    // Basic Auth
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> {
                    auth
                            .requestMatchers("/api/register").permitAll()
                            .requestMatchers("/api/login").permitAll()
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
