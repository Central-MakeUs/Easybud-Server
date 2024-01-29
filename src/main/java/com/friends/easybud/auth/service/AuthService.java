package com.friends.easybud.auth.service;

import com.friends.easybud.jwt.dto.JwtToken;

public interface AuthService {

    JwtToken reissueToken(String refreshToken);

}