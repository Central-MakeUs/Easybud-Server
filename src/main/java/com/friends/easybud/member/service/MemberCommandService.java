package com.friends.easybud.member.service;

import com.friends.easybud.auth.dto.KakaoUserInfo;
import com.friends.easybud.jwt.dto.JwtToken;

public interface MemberCommandService {

    JwtToken kakaoLogin(KakaoUserInfo kakaoUserInfo);

}
