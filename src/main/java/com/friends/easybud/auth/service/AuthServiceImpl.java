package com.friends.easybud.auth.service;

import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.jwt.service.JwtTokenProvider;
import com.friends.easybud.member.service.MemberQueryService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberQueryService memberQueryService;

    @Override
    public JwtToken reissueToken(String refreshToken) {
        validateRefreshToken(refreshToken);

        // TODO 기존 리프레시 토큰 삭제

        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        String uid = claims.getSubject();
        UserDetails userDetails = memberQueryService.getMemberByUid(uid);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities());

        return jwtTokenProvider.generateToken(authentication);
    }

    private void validateRefreshToken(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        // TODO if (redisService.getValue(refreshToken) == null) throw new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND);
    }

}
