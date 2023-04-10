package com.everyparking.server.data.dto;

import java.util.List;

public class ParkingDto {

    public static class ParkingInfo {

        private String name;

        private int total;

        private int available;

        private int used;

    }
    public static class Response {

        private Long id;

        private List<ParkingInfo> parkingInfoList;
    }

//    public static
    public static class ParkingDetailInfo extends ParkingInfo{

//        사용중인 member info
//        잔여시간

    }


}
