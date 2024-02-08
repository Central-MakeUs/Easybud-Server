package com.friends.easybud.auth.service;

import com.friends.easybud.auth.dto.IdTokenRequest;
import com.friends.easybud.auth.dto.SocialLoginResponse;
import com.friends.easybud.member.domain.SocialProvider;

public interface AuthService {

    SocialLoginResponse socialLogin(SocialProvider provider, IdTokenRequest request);

}