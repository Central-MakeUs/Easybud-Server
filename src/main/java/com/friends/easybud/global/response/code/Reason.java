package com.friends.easybud.global.response.code;

import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Reason {

    private HttpStatus httpStatus;
    private final boolean isSuccess;
    private final Integer code;
    private final String message;
    private final HashMap<String, String> result;

}
