package com.jwtauth.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// At the Application startup Spring-Security will try to look for a '@Bean' of type 'SecurityFilterChain', and
// this 'SecurityFilterChain' @Bean is responsible for configuring all the HTTP Security of our Application.
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // 'final', So it will be automatically injected by Spring.
    private final AuthenticationProvider authenticationProvider; // 'final', So it will be automatically injected by Spring.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(httpRequest -> httpRequest.requestMatchers("").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//  ".addFilterBefore()" :- We want to execute 'JwtAuthenticationFilter' filter before the filter call 'UsernamePasswordAuthenticationFilter',
//  because in 'JwtAuthenticationFilter' filter we check everything, and then we set/update the 'SecurityContextHolder'
//  and after that we are call the 'UsernamePasswordAuthenticationToken'.

        return http.build();
    }
}
