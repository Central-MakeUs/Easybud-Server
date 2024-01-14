package com.friends.easybud.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfo {

    private String sub;
    private String nickname;
    private String picture;
    private String email;
    private Boolean emailVerified;

}
