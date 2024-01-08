package com.friends.easybud.jwt.service;

import com.friends.easybud.jwt.dto.JwtToken;
import org.springframework.security.core.Authentication;

public interface TokenService {

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean validateToken(String token);

}