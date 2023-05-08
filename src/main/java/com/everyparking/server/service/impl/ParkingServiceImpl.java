package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.ParkingDto;
import com.everyparking.server.data.dto.ParkingDto.MyParkingStatus;
import com.everyparking.server.data.entity.ParkingInfo;
import com.everyparking.server.data.repository.ParkingInfoRepository;
import com.everyparking.server.data.repository.ParkingLotRepository;
import com.everyparking.server.exception.UserNotFoundException;
import com.everyparking.server.service.ParkingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ParkingServiceImpl implements ParkingService {

    private final ParkingLotRepository parkingLotRepository;

    private final ParkingInfoRepository parkingInfoRepository;

    /**
     * uerId로 MyParkingStatus 검색
     * @param userId
     * @return ParkingDto.MyParkingStatus
     */
    @Override
    public ParkingDto.MyParkingStatus findByUserId(String userId) {

        try {
            ParkingInfo parkingInfo = parkingInfoRepository.findByMember_Id(userId)
                .orElseThrow(
                    () -> new UserNotFoundException());

            return MyParkingStatus.toDto(parkingInfo);

        } catch (UserNotFoundException e) {
            log.info("[ParkingService] {}", e.toString());

        } catch (Exception e) {
            log.info("[ParkingService] {}", e.toString());

        }

        throw new IllegalStateException("findByUserId Error");
    }


}
