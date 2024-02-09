package com.friends.easybud.category.service;

import com.friends.easybud.category.domain.SecondaryCategory;
import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.category.repository.SecondaryCategoryRepository;
import com.friends.easybud.category.repository.TertiaryCategoryRepository;
import com.friends.easybud.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final SecondaryCategoryRepository secondaryCategoryRepository;
    private final TertiaryCategoryRepository tertiaryCategoryRepository;

    @Override
    public List<TertiaryCategory> getTertiaryCategories(Member member) {
        return tertiaryCategoryRepository.findByMemberIdOrIsDefaultTrue(member.getId());
    }

    @Override
    public List<SecondaryCategory> getSecondaryCategories() {
        return secondaryCategoryRepository.findAll();
    }

}
