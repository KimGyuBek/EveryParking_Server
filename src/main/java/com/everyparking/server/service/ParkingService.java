package com.everyparking.server.service;

import com.everyparking.server.data.dto.ParkingDto;
import com.everyparking.server.data.entity.ParkingLot;
import org.springframework.stereotype.Service;

@Service
public interface ParkingService {


    ParkingDto.MyParkingStatus findByUserId(String userId);

    /*ParkingLotId로 ParkingLot 조회*/
    ParkingLot findParkingLotByParkingLotId(Long parkingLotId);

    ParkingDto.ParkingLotMap findParkingLotMap(ParkingLot parkingLot);
}
