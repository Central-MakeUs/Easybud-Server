package com.friends.easybud.global.annotation;

import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.jwt.JwtProvider;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.service.MemberQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final MemberQueryService memberQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final boolean isRegUserAnnotation = parameter.getParameterAnnotation(AuthUser.class) != null;
        final boolean isMember = parameter.getParameterType().equals(Member.class);
        return isRegUserAnnotation && isMember;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new GeneralException(ErrorStatus.AUTHENTICATION_REQUIRED);
        }

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = jwtProvider.resolveToken(request);

        if (!StringUtils.hasText(token) || !jwtProvider.validateToken(token)) {
            throw new GeneralException(ErrorStatus.TOKEN_INVALID);
        }

        String uid = jwtProvider.getAuthentication(token).getName();
        return memberQueryService.getMemberByUid(uid);
    }
}
