package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.IdTokenRequest;
import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.member.domain.SocialProvider;

public interface AuthService {

    JwtToken socialLogin(SocialProvider provider, IdTokenRequest request);

}