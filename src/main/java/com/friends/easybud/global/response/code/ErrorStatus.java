package com.friends.easybud.global.response.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    // 서버 오류
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, 5000, "서버 에러, 쑤에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, 4000, "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, 4001, "로그인이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, 4002, "금지된 요청입니다."),

    // Member: 4050 ~ 4099
    MEMBER_NOT_FOUND(NOT_FOUND, 4050, "존재하지 않는 회원입니다."),

    // Token: 4100 ~ 4149
    TOKEN_INVALID(BAD_REQUEST, 4100, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(BAD_REQUEST, 4101, "만료된 토큰입니다."),
    TOKEN_UNSUPPORTED(BAD_REQUEST, 4102, "지원되지 않는 토큰 형식입니다."),
    TOKEN_CLAIMS_EMPTY(BAD_REQUEST, 4103, "토큰 클레임이 비어있습니다."),
    REFRESH_TOKEN_NOT_FOUND(BAD_REQUEST, 4104, "헤더에 refresh token이 존재하지 않습니다."),
    AUTHENTICATION_REQUIRED(BAD_REQUEST, 4105, "인증 정보가 필요합니다."),

    // AccountCategory: 4150 ~ 4199
    PRIMARY_CATEGORY_NOT_FOUND(NOT_FOUND, 4150, "존재하지 않는 계정 대분류입니다."),
    SECONDARY_CATEGORY_NOT_FOUND(NOT_FOUND, 4151, "존재하지 않는 계정 중분류입니다."),
    TERTIARY_CATEGORY_NOT_FOUND(NOT_FOUND, 4152, "존재하지 않는 계정 소분류입니다."),
    CANNOT_DELETE_DEFAULT_CATEGORY(BAD_REQUEST, 4153, "기본값은 삭제할 수 없습니다."),
    TERTIARY_CATEGORY_ALREADY_EXISTS(BAD_REQUEST, 4154, "이미 존재하는 계정 소분류입니다."),
    UNAUTHORIZED_TERTIARY_CATEGORY_ACCESS(BAD_REQUEST, 4155, "접근 권한이 없는 소분류입니다."),

    // Card: 4200 ~ 4249
    CARD_NOT_FOUND(NOT_FOUND, 4200, "존재하지 않는 카드입니다."),
    UNAUTHORIZED_CARD_ACCESS(BAD_REQUEST, 4201, "접근 권한이 없는 카드입니다."),

    // Transaction: 4250 ~ 4299
    TRANSACTION_NOT_FOUND(NOT_FOUND, 4250, "존재하지 않는 거래입니다."),
    UNAUTHORIZED_TRANSACTION_ACCESS(BAD_REQUEST, 4251, "접근 권한이 없는 거래입니다."),

    // Account: 4300 ~ 4349
    ACCOUNT_NOT_FOUND(NOT_FOUND, 4300, "존재하지 않는 계정입니다."),
    ACCOUNT_CREATION_RULE_VIOLATION(BAD_REQUEST, 4301, "cardId 또는 tertiaryCategoryId 중 하나만 있어야 합니다."),

    // Auth: 4350 ~ 4399
    OAUTH_PROVIDER_NOT_FOUND(BAD_REQUEST, 4350, "OAuth 제공자를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    @Override
    public Reason getReason() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public Reason getReasonHttpStatus() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }

}
