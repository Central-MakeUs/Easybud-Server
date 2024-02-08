package com.friends.easybud.card.controller;

import com.friends.easybud.card.converter.CardConverter;
import com.friends.easybud.card.dto.CardRequest.CardCreateDto;
import com.friends.easybud.card.dto.CardRequest.CardUpdateDto;
import com.friends.easybud.card.dto.CardResponse.CardDto;
import com.friends.easybud.card.dto.CardResponse.CardListDto;
import com.friends.easybud.card.service.CardCommandService;
import com.friends.easybud.card.service.CardQueryService;
import com.friends.easybud.global.annotation.AuthMember;
import com.friends.easybud.global.response.ResponseDto;
import com.friends.easybud.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/cards")
@RestController
@ApiResponses({
        @ApiResponse(responseCode = "2000", description = "성공"),
        @ApiResponse(responseCode = "4200", description = "존재하지 않는 카드입니다."),
        @ApiResponse(responseCode = "4201", description = "접근 권한이 없는 카드입니다."),
        @ApiResponse(responseCode = "5000", description = "서버 에러, 쑤에게 문의 바랍니다.")
})
@Tag(name = "Card API", description = "카드 API")
public class CardController {

    private final CardCommandService cardCommandService;
    private final CardQueryService cardQueryService;

    @Operation(summary = "카드 생성", description = "새로운 카드를 생성합니다.")
    @PostMapping
    public ResponseDto<Long> createCard(@AuthMember Member member, @RequestBody CardCreateDto request) {
        return ResponseDto.onSuccess(cardCommandService.createCard(member, request));
    }

    @Operation(summary = "카드 삭제", description = "기존의 카드를 삭제합니다.")
    @DeleteMapping("/{cardId}")
    public ResponseDto<Boolean> deleteCard(@AuthMember Member member, @PathVariable Long cardId) {
        return ResponseDto.onSuccess(cardCommandService.deleteCard(member, cardId));
    }

    @Operation(summary = "카드 수정", description = "기존의 카드를 수정합니다.")
    @PutMapping("/{cardId}")
    public ResponseDto<Long> updateCard(@AuthMember Member member, @PathVariable Long cardId,
                                        @RequestBody CardUpdateDto request) {
        return ResponseDto.onSuccess(cardCommandService.updateCard(member, cardId, request));
    }

    @Operation(summary = "카드 조회", description = "특정 카드를 조회합니다.")
    @GetMapping("/{cardId}")
    public ResponseDto<CardDto> getCard(@AuthMember Member member, @PathVariable Long cardId) {
        return ResponseDto.onSuccess(CardConverter.toCardDto(cardQueryService.getCard(member, cardId)));
    }

    @Operation(summary = "카드 목록 조회", description = "특정 회원의 카드 목록을 조회합니다.")
    @GetMapping
    public ResponseDto<CardListDto> getCards(@AuthMember Member member) {
        return ResponseDto.onSuccess(CardConverter.toCardListDto(cardQueryService.getCards(member)));
    }

}
