package com.friends.easybud.global;

import com.friends.easybud.global.response.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/health")
    public ResponseDto<String> healthCheck() {
        return ResponseDto.onSuccess("Health check passed successfully!");
    }

}
