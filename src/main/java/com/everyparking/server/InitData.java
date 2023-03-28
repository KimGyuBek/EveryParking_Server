package com.everyparking.server;

import com.everyparking.server.data.entity.Car;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.entity.RoleType;
import com.everyparking.server.data.repository.CarRepository;
import com.everyparking.server.data.repository.MemberRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final MemberRepository memberRepository;
    private final CarRepository carRepoaitory;


    @PostConstruct
    private void initMember() {

        Member member = new Member();

        member.setRoleType(RoleType.ADMIN);
        Car car = new Car();
        car.setCarNumber("1234");
        member.setCar(car);

        memberRepository.save(member);

        List<Member> findAll = memberRepository.findAll();
        for (Member member1 : findAll) {
            log.info("{}", member1.getCar().getCarNumber());

        }

    }


}
