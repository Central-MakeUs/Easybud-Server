package com.friends.easybud.member.service;

import com.friends.easybud.member.domain.Member;

public interface MemberQueryService {

    Member getMemberByUid(String uid);

}
