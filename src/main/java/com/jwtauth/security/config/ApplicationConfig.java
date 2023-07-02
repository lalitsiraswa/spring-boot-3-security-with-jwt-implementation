package com.jwtauth.security.config;

import com.jwtauth.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
            }
        };
    }

    //    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
//    }

    //  The AuthenticationProvider is the "DataAccessObject" which is responsible for to fetch the user details and
    //  also encode password.
    @Bean
    public AuthenticationProvider authenticationProvider() {
//      DataAccessObjectAuthenticationProvider (DaoAuthenticationProvider)
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//      We need to tell this AuthenticationProvider which UserDetails Service to use in oder to fetch information
//      about our User.
        authenticationProvider.setUserDetailsService(userDetailsService());
//      Need to specify which passwordEncoder we are using within our Application.
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    //  AuthenticationManager is responsible to Manage Authentication.
//  The AuthenticationManager have bunch of Methods for Authentication, One of the method that allow us to
//  Authenticate User based on 'Username' and 'Password'.
//  UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
//  authenticationManager.authenticate(authenticationToken);
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
