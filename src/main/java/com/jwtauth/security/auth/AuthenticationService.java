package com.jwtauth.security.auth;

import com.jwtauth.security.config.JwtService;
import com.jwtauth.security.user.Role;
import com.jwtauth.security.user.User;
import com.jwtauth.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository; // 'final', So it will be automatically injected by Spring.
    private final PasswordEncoder passwordEncoder; // 'final', So it will be automatically injected by Spring.
    private final JwtService jwtService; // 'final', So it will be automatically injected by Spring.

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
