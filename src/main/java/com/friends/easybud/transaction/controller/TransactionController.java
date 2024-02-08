package com.friends.easybud.transaction.controller;

import com.friends.easybud.global.annotation.ApiErrorCodeExample;
import com.friends.easybud.global.annotation.AuthMember;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.transaction.converter.TransactionConverter;
import com.friends.easybud.transaction.dto.TransactionRequest.TransactionCreateDto;
import com.friends.easybud.transaction.dto.TransactionResponse.TransactionDto;
import com.friends.easybud.transaction.dto.TransactionResponse.TransactionListDto;
import com.friends.easybud.transaction.service.TransactionCommandService;
import com.friends.easybud.transaction.service.TransactionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
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
@RequestMapping("/api/transactions")
@RestController
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "Transaction API", description = "거래 API")
public class TransactionController {

    private final TransactionCommandService transactionCommandService;
    private final TransactionQueryService transactionQueryService;

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus.CARD_NOT_FOUND,
            ErrorStatus.TERTIARY_CATEGORY_NOT_FOUND,
            ErrorStatus.ACCOUNT_CREATION_RULE_VIOLATION,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "거래 생성", description = "새로운 거래를 생성합니다. 카드 ID와 소분류 ID 중 하나만 입력해 주세요.")
    @PostMapping
    public ResponseDto<Long> createTransaction(@AuthMember Member member,
                                               @RequestBody TransactionCreateDto request) {
        return ResponseDto.onSuccess(transactionCommandService.createTransaction(member, request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "거래 삭제", description = "기존의 거래를 삭제합니다.")
    @DeleteMapping("/{transactionId}")
    public ResponseDto<Boolean> deleteTransaction(@AuthMember Member member,
                                                  @PathVariable Long transactionId) {
        return ResponseDto.onSuccess(transactionCommandService.deleteTransaction(member, transactionId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus.TRANSACTION_NOT_FOUND,
            ErrorStatus.UNAUTHORIZED_TRANSACTION_ACCESS,
            ErrorStatus.TRANSACTION_NOT_FOUND,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "거래 조회", description = "특정 거래를 조회합니다.")
    @GetMapping("/{transactionId}")
    public ResponseDto<TransactionDto> getTransaction(@AuthMember Member member,
                                                      @PathVariable Long transactionId) {
        return ResponseDto.onSuccess(
                TransactionConverter.toTransactionDto(transactionQueryService.getTransaction(member, transactionId)));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "특정 날짜의 거래 조회", description = "주어진 날짜에 해당하는 모든 거래 목록을 조회합니다.")
    @GetMapping("/date/{date}")
    public ResponseDto<TransactionListDto> getTransactionsByDate(@AuthMember Member member,
                                                                 @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return ResponseDto.onSuccess(
                TransactionConverter.toTransactionListDto(
                        transactionQueryService.getTransactionsBetweenDates(member, startOfDay, endOfDay)));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "최근 3개의 거래 조회", description = "가장 최근에 이루어진 3개의 거래를 조회합니다.")
    @GetMapping("/recent")
    public ResponseDto<TransactionListDto> getRecentTransactions(@AuthMember Member member) {
        return ResponseDto.onSuccess(
                TransactionConverter.toTransactionListDto(transactionQueryService.getRecentTransactions(member)));
    }

    @ApiErrorCodeExample({
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.AUTHENTICATION_REQUIRED,
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "특정 연도와 달의 거래 조회", description = "주어진 연도와 달에 해당하는 모든 거래 목록을 조회합니다.")
    @GetMapping("/year/{year}/month/{month}")
    public ResponseDto<TransactionListDto> getTransactionsByYearAndMonth(
            @AuthMember Member member,
            @PathVariable int year,
            @PathVariable int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        LocalDateTime startDateTime = startOfMonth.atStartOfDay();
        LocalDateTime endDateTime = endOfMonth.atTime(23, 59, 59);

        return ResponseDto.onSuccess(
                TransactionConverter.toTransactionListDto(
                        transactionQueryService.getTransactionsBetweenDates(member, startDateTime, endDateTime)));
    }

}
