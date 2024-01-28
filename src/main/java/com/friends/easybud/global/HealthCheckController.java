package com.friends.easybud.global;

import com.friends.easybud.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RequestMapping("/health")
@RestController
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseDto<String> healthCheck() {
        return ResponseDto.onSuccess("Health check passed successfully!");
    }

    @GetMapping("/db")
    public String checkHealth() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return "Database Connection: OK";
        } catch (Exception e) {
            return "Database Connection: Failed - " + e.getMessage();
        }
    }

    @GetMapping("/test-error")
    public ResponseEntity<String> testInternalServerError() {
        throw new RuntimeException("내부 서버 오류 테스트");
    }

}