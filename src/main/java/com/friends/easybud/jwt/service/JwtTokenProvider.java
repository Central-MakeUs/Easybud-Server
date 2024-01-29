package com.friends.easybud.jwt.service;

import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.jwt.dto.JwtToken;
import com.friends.easybud.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final RedisService redisService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, RedisService redisService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisService = redisService;
    }

    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(new Date(now + 1800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(now + 604800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        redisService.setValue(refreshToken, authentication.getName(), Duration.ofDays(7).toMillis());

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (SecurityException | MalformedJwtException e) {
            throw new GeneralException(ErrorStatus.TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            throw new GeneralException(ErrorStatus.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new GeneralException(ErrorStatus.TOKEN_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus.TOKEN_CLAIMS_EMPTY);
        }
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
