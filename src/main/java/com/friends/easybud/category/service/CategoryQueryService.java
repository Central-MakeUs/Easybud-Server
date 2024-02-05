package com.friends.easybud.category.service;

import com.friends.easybud.category.domain.TertiaryCategory;
import com.friends.easybud.member.domain.Member;
import java.util.List;

public interface CategoryQueryService {

    List<TertiaryCategory> getTertiaryCategories(Member member);

}
