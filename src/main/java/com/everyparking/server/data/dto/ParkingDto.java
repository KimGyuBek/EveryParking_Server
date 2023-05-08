package com.everyparking.server.data.dto;

import com.everyparking.server.data.entity.ParkingInfo;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * ParkingDto
 */
public class ParkingDto {

    /**
     * 메인화면 - myParkingStatus
     */
    @Data
    @Builder
    public static class MyParkingStatus {

        private int parkingId;
        private int remain;
        private String carNumber;

        /**
         * ParkingInfo -> MyParkingStatus
         * @param parkingInfo
         * @return ParkingDto.MyParkingStatus
         */
        public static MyParkingStatus toDto(ParkingInfo parkingInfo) {
            Duration remain = getRemain(parkingInfo);

            return MyParkingStatus
                .builder()
                .parkingId(parkingInfo.getParkingId())
                .remain(remain.toMinutesPart())
                .carNumber(parkingInfo.getCar().getCarNumber())
                .build();
        }

        private static Duration getRemain(ParkingInfo parkingInfo) {
            Duration remain = Duration.ofDays(
                Duration.between(parkingInfo.getCreatedTime().toLocalTime(),
                    LocalDateTime.now().toLocalTime()).toMinutes());
            return remain;
        }
    }




}
