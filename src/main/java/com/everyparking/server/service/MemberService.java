package com.everyparking.server.service;

import com.everyparking.server.data.dto.MemberDto;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {


    void join(MemberDto.Join joinDto);

    Member login(MemberDto.Login loginDto);
}
