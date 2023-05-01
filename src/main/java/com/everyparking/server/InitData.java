package com.everyparking.server;

import com.everyparking.server.data.entity.Car;
import com.everyparking.server.data.entity.CarStatus;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.entity.Message;
import com.everyparking.server.data.entity.RoleType;
import com.everyparking.server.data.entity.UserInfo;
import com.everyparking.server.data.repository.MemberRepository;
import com.everyparking.server.data.repository.MessageRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final MemberRepository memberRepository;

    private final MessageRepository messageRepository;

//    @PostConstruct
//    private void initData() {
//
//        /*TODO dto 만들어서 builder로 바꿀 예정*/
//        /*TODO Service 로직 만들어서 데이터 추가 예정*/
//
//        /*Member 생성*/
//        Member member1 = generateMember(
//            "user1", "user1", RoleType.USER, new UserInfo(
//                "123-1234-1234", "addr1", "user1@ooo.com"
//            ), "1234");
//
//        Member member2 = generateMember(
//            "user2", "user2", RoleType.USER, new UserInfo(
//                "143-5632-1235", "addr2", "user2@ooo.com"
//            ), "1111");
//
//        /*Car 생성*/
//        Car car = generateCar(
//            "1234", "Escalade", CarStatus.APPROVED, member2);
//        member2.setCar(car);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        /*Message 생성*/
//        Message message = generateMessage(member1, member2, "message1");
//        messageRepository.save(message);
//
//        Message findBySender = messageRepository.findBySender(member1);
//        log.info("{}", findBySender.getDetails());
//
//    }

    private Message generateMessage(Member sender, Member receiver, String detail) {
        Message message = new Message();
        message.setDetails(detail);
        message.setSender(sender);
        message.setReceiver(receiver);
        return message;
    }

//    private Car generateCar(String carNumber, String modelName,
//        CarStatus carStatus, Member member) {
//        Car car = new Car();
//        car.setCarNumber(carNumber);
//        car.setModelName(modelName);
//        car.setCarStatus(carStatus);
//        car.setMember(member);
//
//        return car;
//    }

//    private Member generateMember(String userId, String userName, RoleType roleType,
//        UserInfo userInfo, String password) {
//        Member member = new Member();
//        member.setUserId(userId);
//        member.setUserName(userName);
//        member.setPassword(password);
//        member.setRoleType(roleType);
//        member.setUserInfo(
//            userInfo
//        );
//        return member;
//    }


}
