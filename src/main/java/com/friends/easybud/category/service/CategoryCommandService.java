package com.friends.easybud.category.service;

import static com.friends.easybud.category.dto.CategoryRequest.TertiaryCategoryCreateDto;

public interface CategoryCommandService {

    Long createTertiaryCategory(TertiaryCategoryCreateDto request);

    Boolean deleteTertiaryCategory(Long accountCategoryId);

}
