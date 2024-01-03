package com.friends.easybud.global.exception;

import com.friends.easybud.global.response.code.BaseCode;
import com.friends.easybud.global.response.code.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private BaseCode code;

    public Reason getErrorReason() {
        return this.code.getReason();
    }

    public Reason getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
