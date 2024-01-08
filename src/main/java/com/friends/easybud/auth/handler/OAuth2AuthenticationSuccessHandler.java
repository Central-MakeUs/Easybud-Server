package com.friends.easybud.auth.handler;

import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.jwt.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtToken jwtToken = tokenService.generateToken(authentication);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("accessToken", jwtToken.getAccessToken());
        jsonResponse.put("refreshToken", jwtToken.getRefreshToken());

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }

}
