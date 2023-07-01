package com.jwtauth.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String extractUsername(String jwtToken) {
        return null;
    }

    private Claims extractAllClaims(String jwtToken) {
//        The 'SigningKey' is used to create the 'signature part' of the JWT Token, which is used to verify that the
//        sender of the JWT token is Who it claims to be and ensure that the message wasn't change along the way.
//        So, we want to ensure that the same person or the same client that is sending this JWT key is one that claims
//        who to be.
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(jwtToken)
                .getBody();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
