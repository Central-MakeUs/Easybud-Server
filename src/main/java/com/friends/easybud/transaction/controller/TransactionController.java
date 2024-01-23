package com.friends.easybud.transaction.controller;

import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.transaction.dto.TransactionRequest.TransactionCreateDto;
import com.friends.easybud.transaction.dto.TransactionResponse.TransactionDto;
import com.friends.easybud.transaction.dto.TransactionResponse.TransactionListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/transactions")
@RestController
@Tag(name = "Transaction API", description = "거래 API")
public class TransactionController {

    @Operation(summary = "거래 생성", description = "새로운 거래를 생성합니다.")
    @PostMapping
    public ResponseDto<Long> createTransaction(@RequestBody TransactionCreateDto request) {
        return ResponseDto.onSuccess(1L);
    }

    @Operation(summary = "거래 삭제", description = "새로운 거래를 삭제합니다.")
    @DeleteMapping("/{transactionId}")
    public ResponseDto<Boolean> deleteTransaction(@PathVariable Long transactionId) {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

    @Operation(summary = "거래 조회", description = "특정 거래를 조회합니다.")
    @GetMapping("/{transactionId}")
    public ResponseDto<TransactionDto> getTransaction(@PathVariable Long transactionId) {
        return ResponseDto.onSuccess(null);
    }

    @Operation(summary = "특정 날짜의 거래 조회", description = "주어진 날짜에 해당하는 모든 거래 목록을 조회합니다.")
    @GetMapping("/date/{date}")
    public ResponseDto<TransactionListDto> getTransactionsByDate(
            @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime date) {
        return ResponseDto.onSuccess(null);
    }

    @Operation(summary = "최근 3개의 거래 조회", description = "가장 최근에 이루어진 3개의 거래를 조회합니다.")
    @GetMapping("/recent")
    public ResponseDto<TransactionListDto> getRecentTransactions() {
        return ResponseDto.onSuccess(null);
    }

}
