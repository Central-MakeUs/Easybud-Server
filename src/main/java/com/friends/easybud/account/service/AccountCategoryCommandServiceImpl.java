//package com.friends.easybud.account.service;
//
//import com.friends.easybud.account.dto.AccountCategoryRequest.AccountCategoryCreateDto;
//import com.friends.easybud.account.repository.AccountCategoryRepository;
//import com.friends.easybud.global.exception.GeneralException;
//import com.friends.easybud.global.response.code.ErrorStatus;
//import com.friends.easybud.member.domain.Member;
//import com.friends.easybud.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@RequiredArgsConstructor
//@Transactional
//@Service
//public class AccountCategoryCommandServiceImpl implements AccountCategoryCommandService {
//
//    private final AccountCategoryRepository accountCategoryRepository;
//    private final MemberRepository memberRepository;    // TODO MemberQueryService 주입
//
//    @Override
//    public Long createAccountCategory(AccountCategoryCreateDto request) {
//        Member member = memberRepository.findById(1L).get();    // TODO 로그인 된 사용자 정보 조회
//        AccountCategory accountCategory = buildAccountCategory(request, member);
//        accountCategoryRepository.save(accountCategory);
//        return accountCategory.getId();
//    }
//
//    private AccountCategory buildAccountCategory(AccountCategoryCreateDto request, Member member) {
//        return AccountCategory.builder()
//                .primaryCategory(request.getPrimaryCategory())
//                .secondaryCategory(request.getSecondaryCategory())
//                .tertiaryCategory(request.getTertiaryCategory())
//                .quaternaryCategory(request.getQuaternaryCategory())
//                .member(member)
//                .build();
//    }
//
//    @Override
//    public Boolean deleteAccountCategory(Long accountCategoryId) {
//        AccountCategory accountCategory = accountCategoryRepository.findById(accountCategoryId)
//                .orElseThrow(() -> new GeneralException(ErrorStatus.ACCOUNT_CATEGORY_NOT_FOUND));
//        accountCategoryRepository.delete(accountCategory);
//        return Boolean.TRUE;
//    }
//
//}
