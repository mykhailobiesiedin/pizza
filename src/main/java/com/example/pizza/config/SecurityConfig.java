package com.example.pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(x -> x.disable())
                .authorizeHttpRequests(
                        x -> x
                                .requestMatchers(HttpMethod.GET, "/cafe/all")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/pizza/all")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/cafe/create")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/pizza/create")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/cafe/chain")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/cafe/name-address")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/cafe/id")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/cafe/update")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cafe/delete-by-id")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cafe/delete-by-name-address")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cafe/delete-chain")
                                .hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
