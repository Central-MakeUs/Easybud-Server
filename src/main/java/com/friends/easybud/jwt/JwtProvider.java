package com.friends.easybud.jwt;

import com.friends.easybud.auth.dto.RefreshTokenRequest;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.service.MemberQueryService;
import com.friends.easybud.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtProvider {

    private final Key key;
    private final RedisService redisService;
    private final MemberQueryService memberQueryService;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, RedisService redisService,
                       MemberQueryService memberQueryService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisService = redisService;
        this.memberQueryService = memberQueryService;
    }

    public JwtDto generateToken(Authentication authentication) {
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

        return JwtDto.builder()
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

    public JwtDto reissueToken(RefreshTokenRequest request) {
        validateRefreshToken(request.getRefreshToken());

        redisService.deleteValue(request.getRefreshToken());

        Claims claims = parseClaims(request.getRefreshToken());
        String uid = claims.getSubject();
        UserDetails userDetails = memberQueryService.getMemberByUid(uid);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities());

        return generateToken(authentication);
    }

    private void validateRefreshToken(String refreshToken) {
        validateToken(refreshToken);
        if (redisService.getValue(refreshToken) == null) {
            throw new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    public boolean logout(String refreshToken) {
        redisService.deleteValue(refreshToken);
        return true;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


}
