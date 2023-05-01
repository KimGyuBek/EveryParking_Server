package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.MemberDto;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.repository.MemberRepository;
import com.everyparking.server.exception.InvalidPwdException;
import com.everyparking.server.exception.UserNotFoundException;
import com.everyparking.server.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     *
     * @param joinDto
     */
    @Override
    @Transactional
    public void join(MemberDto.Join joinDto) {

        Member member = joinDto.toEntity(joinDto);

        try {
            memberRepository.save(member);

            log.info("[MemberService] {} 가입 성공", joinDto.toString());
        } catch (Exception e) {
            log.info(e.toString());
        }
//
        log.info(member.toString());

    }

    /**
     * 로그인
     *
     * @param loginDto
     */
    @Override
    public Member login(MemberDto.Login loginDto) {
//        Member findMember = memberRepository.findByUserId(loginDto.getUserId()).orElseGet(null);
        Member findMember = memberRepository.findByUserId(loginDto.getUserId())
            .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원"));
        log.info("[MemberService] {}", findMember.toString());
        if (findMember != null) {
            if (findMember.getPassword().equals(loginDto.getPassword())) {
                log.info("[MemberService] 로그인 성공");
                return findMember;
            } else {
//                log.info("[MemberService] 비밀번호 오류");
                throw new InvalidPwdException("비밀번호 오류");
            }
        } else {
//            log.info("[MemberService] 존재하지 않는 회원");
//            throw new UserNotFoundException("존재하지 않는 회원");

            return null;
        }

    }
}
