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


/**
 * Configuration class for Spring Security settings.
 * This class is annotated with {@code @Configuration}, {@code @EnableMethodSecurity},
 * and {@code @EnableWebSecurity} to enable and customize Spring Security features.
 * It also defines a {@code BCryptPasswordEncoder} bean for password encoding.
 *
 * @see Configuration
 * @see EnableMethodSecurity
 * @see EnableWebSecurity
 */
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Provides a BCryptPasswordEncoder bean for password encoding.
     *
     * @return A {@code BCryptPasswordEncoder} instance.
     * @see BCryptPasswordEncoder
     * @see Bean
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Configures the security filter chain for the application.
     * This method defines the security rules for various endpoints, specifying the required roles
     * or permissions for access. Additionally, it disables CSRF protection for simplicity.
     * The configuration covers specific HTTP methods for different endpoints, allowing or restricting
     * access based on user roles.
     *
     * @param http The {@code HttpSecurity} object to configure the security settings.
     * @return A {@code SecurityFilterChain} configured with the specified security rules.
     * @throws Exception If an error occurs during the configuration process.
     * @see HttpSecurity
     * @see SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(x -> x.disable())
                .authorizeHttpRequests(
                        x -> x
                                // Permit all for specific GET endpoints
                                .requestMatchers(HttpMethod.GET, "/cafe/all")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/pizza/all")
                                .permitAll()
                                // Admin-only access for specific POST, PUT, and DELETE endpoints
                                .requestMatchers(HttpMethod.POST, "/cafe/create")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/pizza/create")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/pizza/delete-pizza-by-name")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/pizza/update")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/cafe/update")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cafe/delete-by-id")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cafe/delete-by-name-address")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cafe/delete-chain")
                                .hasRole("ADMIN")
                                // Access for both ADMIN and USER roles for specific GET endpoints
                                .requestMatchers(HttpMethod.GET, "/pizza/id")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/pizza/name")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/cafe/chain")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/cafe/name-address")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/cafe/id")
                                .hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
