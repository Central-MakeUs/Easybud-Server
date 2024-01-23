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
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, 5000, "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, 4000, "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, 4001, "로그인이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, 4002, "금지된 요청입니다."),

    // Member: 4050 ~ 4099
    MEMBER_NOT_FOUND(NOT_FOUND, 4050, "존재하지 않는 회원입니다."),

    // AccountCategory: 4100 ~ 4149
    PRIMARY_CATEGORY_NOT_FOUND(NOT_FOUND, 4100, "존재하지 않는 계정 대분류입니다."),
    SECONDARY_CATEGORY_NOT_FOUND(NOT_FOUND, 4101, "존재하지 않는 계정 중분류입니다."),
    TERTIARY_CATEGORY_NOT_FOUND(NOT_FOUND, 4102, "존재하지 않는 계정 소분류입니다."),
    CANNOT_DELETE_DEFAULT_CATEGORY(BAD_REQUEST, 4103, "기본값은 삭제할 수 없습니다."),
    TERTIARY_CATEGORY_ALREADY_EXISTS(BAD_REQUEST, 4104, "이미 존재하는 계정 소분류입니다."),

    // Card: 4150 ~ 4199
    CARD_NOT_FOUND(NOT_FOUND, 4150, "존재하지 않는 카드입니다."),

    // Transaction: 4200 ~ 4249
    TRANSACTION_NOT_FOUND(NOT_FOUND, 4200, "존재하지 않는 거래입니다."),

    // Account: 4250 ~ 4299
    ACCOUNT_NOT_FOUND(NOT_FOUND, 4250, "존재하지 않는 계정입니다."),
    ACCOUNT_CREATION_RULE_VIOLATION(BAD_REQUEST, 4251, "cardId 또는 tertiaryCategoryId 중 하나만 있어야 합니다.");


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
