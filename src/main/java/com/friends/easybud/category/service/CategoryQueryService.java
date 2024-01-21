package com.friends.easybud.category.service;

import com.friends.easybud.category.domain.TertiaryCategory;
import java.util.List;

public interface CategoryQueryService {

    List<TertiaryCategory> getTertiaryCategories(Long memberId);

}
