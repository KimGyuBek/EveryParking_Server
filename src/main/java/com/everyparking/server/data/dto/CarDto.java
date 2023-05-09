package com.everyparking.server.data.dto;

import com.everyparking.server.data.entity.Car;
import com.everyparking.server.data.entity.CarEnterStatus;
import com.everyparking.server.data.entity.CarStatus;
import com.everyparking.server.data.entity.Member;
import lombok.Builder;
import lombok.Data;

public class CarDto {

    @Data
    @Builder
    public static class Register {

//        private String userId;

        private String carNumber;

        private String modelName;

        public Car toEntity(CarDto.Register register, Member member) {
            return
                Car.builder()
                    .carNumber(register.carNumber)
                    .modelName(register.modelName)
                    .carStatus(CarStatus.WAITING)
                    .member(member)
                    .carEnterStatus(new CarEnterStatus(-1, false))
                    .build();
        }

    }


}
