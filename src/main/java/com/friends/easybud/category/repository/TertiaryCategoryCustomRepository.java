package com.friends.easybud.category.repository;

public interface TertiaryCategoryCustomRepository {

    Boolean existsWithConditions(Long memberId, String content, Long secondaryCategoryId);

}
