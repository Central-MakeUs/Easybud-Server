package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.IdTokenRequest;
import com.friends.easybud.jwt.JwtDto;
import com.friends.easybud.member.domain.SocialProvider;

public interface AuthService {

    JwtDto socialLogin(SocialProvider provider, IdTokenRequest request);

}