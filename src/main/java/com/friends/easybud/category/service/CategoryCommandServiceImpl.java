package com.friends.easybud.category.service;

import static com.friends.easybud.category.dto.CategoryRequest.TertiaryCategoryCreateDto;

import com.friends.easybud.category.domain.SecondaryCategory;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.repository.SecondaryCategoryRepository;
import com.friends.easybud.category.repository.TertiaryCategoryRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import com.friends.easybud.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final SecondaryCategoryRepository secondaryCategoryRepository;
    private final TertiaryCategoryRepository tertiaryCategoryRepository;
    private final MemberRepository memberRepository;    // TODO MemberQueryService 주입

    @Override
    public Long createTertiaryCategory(TertiaryCategoryCreateDto request) {
        Member member = memberRepository.findById(1L).get();    // TODO 로그인 된 사용자 정보 조회
        SecondaryCategory secondaryCategory = secondaryCategoryRepository.findByContent(request.getSecondaryCategory())
                .orElseThrow(() -> new GeneralException(ErrorStatus.SECONDARY_CATEGORY_NOT_FOUND));

        validateTertiaryCategoryUniqueness(member.getId(), request.getTertiaryCategory(), secondaryCategory.getId());

        TertiaryCategory tertiaryCategory = buildTertiaryCategory(request, secondaryCategory, member);
        tertiaryCategoryRepository.save(tertiaryCategory);
        return tertiaryCategory.getId();
    }

    private void validateTertiaryCategoryUniqueness(Long memberId, String tertiaryCategoryContent,
                                                    Long secondaryCategoryId) {
        boolean exists = tertiaryCategoryRepository.existsByMemberIdOrIsDefaultAndContentAndSecondaryCategoryId(
                memberId, tertiaryCategoryContent, secondaryCategoryId
        );
        if (exists) {
            throw new GeneralException(ErrorStatus.TERTIARY_CATEGORY_ALREADY_EXISTS);
        }
    }

    private TertiaryCategory buildTertiaryCategory(TertiaryCategoryCreateDto request,
                                                   SecondaryCategory secondaryCategory, Member member) {
        return TertiaryCategory.builder()
                .content(request.getTertiaryCategory())
                .isDefault(Boolean.FALSE)
                .secondaryCategory(secondaryCategory)
                .member(member)
                .build();
    }

    @Override
    public Boolean deleteTertiaryCategory(Long tertiaryCategoryId) {
        TertiaryCategory tertiaryCategory = tertiaryCategoryRepository.findById(tertiaryCategoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TERTIARY_CATEGORY_NOT_FOUND));

        tertiaryCategoryRepository.delete(tertiaryCategory);
        return Boolean.TRUE;
    }

}