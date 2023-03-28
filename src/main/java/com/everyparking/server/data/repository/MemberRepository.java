package com.everyparking.server.data.repository;

import com.everyparking.server.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
