package com.friends.easybud.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.friends.easybud.global.response.code.BaseCode;
import com.friends.easybud.global.response.code.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ResponseDto<T> {

    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    private T result;

    public static <T> ResponseDto<T> onSuccess(T result) {
        return new ResponseDto<>(true, 2000, SuccessStatus._SUCCESS.getMessage(), result);
    }

    public static <T> ResponseDto<T> of(BaseCode code, T result) {
        return new ResponseDto<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), result);
    }

    public static <T> ResponseDto<T> onFailure(Integer code, String message, T data){
        return new ResponseDto<>(true, code, message, data);
    }

}