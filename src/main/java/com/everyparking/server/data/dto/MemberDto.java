package com.everyparking.server.data.dto;

import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.entity.RoleType;
import com.everyparking.server.data.entity.UserInfo;
import lombok.Builder;
import lombok.Data;

public class MemberDto {

    /**
     * Member join Dto
     */
    @Builder
    @Data
    public static class Join {

        private Long id;

        private int studentId;

        private String studentName;

        private String userId;

        private String password;

        private int phoneNumber;

        private String email;

        /**
         * Dto -> Entity
         *
         * @param joinDto
         * @return Member
         */
        public Member toEntity(MemberDto.Join joinDto) {
            Member member = Member.builder().id(joinDto.id).userId(joinDto.userId)
                .password(joinDto.password).userName(joinDto.studentName).roleType(RoleType.USER)
                .userInfo(
                    UserInfo.builder().phoneNumber(joinDto.phoneNumber).email(joinDto.email)
                        .build())
                .build();

            return member;
        }
    }

    /**
     * Member login Dto
     */
    @Builder
    @Data
    public static class Login {

        private String userId;

        private String password;
    }


    @Builder
    @Data
    public static class UserInfoDto {

        private String studentName;

        private boolean status;

    }


}
