package com.friends.easybud.category.service;

import static com.friends.easybud.category.dto.CategoryRequest.TertiaryCategoryCreateDto;

import com.friends.easybud.category.domain.SecondaryCategory;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.repository.SecondaryCategoryRepository;
import com.friends.easybud.category.repository.TertiaryCategoryCustomRepository;
import com.friends.easybud.category.repository.TertiaryCategoryRepository;
import com.friends.easybud.global.exception.GeneralException;
import com.friends.easybud.global.response.code.ErrorStatus;
import com.friends.easybud.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final SecondaryCategoryRepository secondaryCategoryRepository;
    private final TertiaryCategoryRepository tertiaryCategoryRepository;
    private final TertiaryCategoryCustomRepository tertiaryCategoryCustomRepository;

    @Override
    public TertiaryCategory createTertiaryCategory(Member member, TertiaryCategoryCreateDto request) {
        SecondaryCategory secondaryCategory = secondaryCategoryRepository.findById(request.getSecondaryCategoryId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.SECONDARY_CATEGORY_NOT_FOUND));

        validateTertiaryCategoryUniqueness(member.getId(), request.getTertiaryCategoryContent(),
                secondaryCategory.getId());

        TertiaryCategory tertiaryCategory = buildTertiaryCategory(request, secondaryCategory, member);
        return tertiaryCategoryRepository.save(tertiaryCategory);
    }

    private void validateTertiaryCategoryUniqueness(Long memberId, String tertiaryCategoryContent,
                                                    Long secondaryCategoryId) {
        boolean exists = tertiaryCategoryCustomRepository.existsWithConditions(
                memberId,
                tertiaryCategoryContent,
                secondaryCategoryId
        );
        if (exists) {
            throw new GeneralException(ErrorStatus.TERTIARY_CATEGORY_ALREADY_EXISTS);
        }
    }

    private TertiaryCategory buildTertiaryCategory(TertiaryCategoryCreateDto request,
                                                   SecondaryCategory secondaryCategory, Member member) {
        return TertiaryCategory.builder()
                .content(request.getTertiaryCategoryContent())
                .isDefault(Boolean.FALSE)
                .secondaryCategory(secondaryCategory)
                .member(member)
                .build();
    }

    @Override
    public Boolean deleteTertiaryCategory(Member member, Long tertiaryCategoryId) {
        TertiaryCategory tertiaryCategory = tertiaryCategoryRepository.findById(tertiaryCategoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TERTIARY_CATEGORY_NOT_FOUND));

        checkTertiaryCategoryOwnership(member, tertiaryCategory);

        if (tertiaryCategory.getIsDefault().equals(Boolean.TRUE)) {
            throw new GeneralException(ErrorStatus.CANNOT_DELETE_DEFAULT_CATEGORY);
        }

        tertiaryCategoryRepository.delete(tertiaryCategory);
        return Boolean.TRUE;
    }

    private void checkTertiaryCategoryOwnership(Member member, TertiaryCategory tertiaryCategory) {
        if (!tertiaryCategory.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED_TERTIARY_CATEGORY_ACCESS);
        }
    }

}