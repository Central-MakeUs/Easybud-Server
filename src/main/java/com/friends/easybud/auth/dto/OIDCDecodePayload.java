package com.friends.easybud.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OIDCDecodePayload {

    private String iss;
    private String aud;
    private String sub;
    private String email;
    private String nickname;

}
