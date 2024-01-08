package com.friends.easybud.auth.controller;

import com.friends.easybud.global.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {

    @RequestMapping("/kakao")
    public ResponseDto<Boolean> kakaoLogin() {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

}
