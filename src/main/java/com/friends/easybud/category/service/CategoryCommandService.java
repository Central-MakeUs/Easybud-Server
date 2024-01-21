package com.friends.easybud.category.service;

import com.friends.easybud.category.dto.CategoryRequest;

public interface CategoryCommandService {

    Long createTertiaryCategory(CategoryRequest.TertiaryCategoryCreateDto request);

    Boolean deleteTertiaryCategory(Long accountCategoryId);

}
