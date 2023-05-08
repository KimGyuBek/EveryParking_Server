package com.everyparking.server.service;

import com.everyparking.server.data.dto.ParkingDto;
import org.springframework.stereotype.Service;

@Service
public interface ParkingService {


    ParkingDto.MyParkingStatus findByUserId(String userId);
}
