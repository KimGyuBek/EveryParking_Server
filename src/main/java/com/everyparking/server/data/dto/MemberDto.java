package com.everyparking.server.data.dto;

import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.entity.RoleType;
import lombok.Data;
import lombok.ToString;

public class MemberDto {

    public static class Login {

        private String email;

        private String password;

    }

    @ToString
    @Data
    public static class Join {


        private String userId;

        private String userName;

        private String password;

        private String phoneNumber;

        private String address;

        private String email;


        public Member toEntity(MemberDto.Join joinDto) {
            Member member = new Member();
            member.setUserId(joinDto.userId);
            member.setPassword(joinDto.password);
            member.setUserName(joinDto.userName);
            member.setRoleType(RoleType.USER);
//            member.setUserInfo(joinDto.);

            return member;
        }

    }

    public static class Info {

        private String name;
    }


}
