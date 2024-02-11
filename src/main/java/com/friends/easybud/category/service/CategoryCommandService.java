package com.friends.easybud.category.service;

import static com.friends.easybud.category.dto.CategoryRequest.TertiaryCategoryCreateDto;

import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.member.domain.Member;

public interface CategoryCommandService {

    TertiaryCategory createTertiaryCategory(Member member, TertiaryCategoryCreateDto request);

    Boolean deleteTertiaryCategory(Member member, Long accountCategoryId);

}
