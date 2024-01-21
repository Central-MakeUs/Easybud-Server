package com.friends.easybud.category.service;

import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.repository.TertiaryCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final TertiaryCategoryRepository tertiaryCategoryRepository;

    @Override
    public List<TertiaryCategory> getTertiaryCategories(Long memberId) {
        return tertiaryCategoryRepository.findByMemberIdOrIsDefaultTrue(memberId);
    }

}
