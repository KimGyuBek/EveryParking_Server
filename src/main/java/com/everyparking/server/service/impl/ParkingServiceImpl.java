package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.ParkingDto;
import com.everyparking.server.data.dto.ParkingDto.MyParkingStatus;
import com.everyparking.server.data.dto.ParkingDto.ParkingInfoDto.Map;
import com.everyparking.server.data.dto.ParkingDto.ParkingLotMap;
import com.everyparking.server.data.entity.ParkingInfo;
import com.everyparking.server.data.entity.ParkingLot;
import com.everyparking.server.data.repository.ParkingInfoRepository;
import com.everyparking.server.data.repository.ParkingLotRepository;
import com.everyparking.server.exception.ParkingLotException;
import com.everyparking.server.exception.UserNotFoundException;
import com.everyparking.server.service.ParkingService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
     * <p>
     * 사용자 주차 정보
     * </p>
     * <p>
     * uerId로 MyParkingStatus 검색
     *
     * @param userId
     * @return ParkingDto.MyParkingStatus
     *
     * </p>
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

    /*ParkingLotId로 ParkingLot 조회*/
    @Override
    public ParkingLot findParkingLotByParkingLotId(Long parkingLotId) {
        try {
            ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(
                () -> new ParkingLotException("일치하는 주차장 없음")
            );

            return parkingLot;

        } catch (Exception e) {
            log.info("[ParkingService] {}", e.toString());
            throw e;
        }
    }

    /*ParkingLot으로 ParkingMap 반환*/
    @Override
    public ParkingDto.ParkingLotMap findParkingLotMap(ParkingLot parkingLot) {
        try {
            List<ParkingInfo> parkingInfoList = parkingInfoRepository.findAllByParkingLot(
                    parkingLot)
                .orElseThrow(
                    () -> new ParkingLotException("일치하는 ParkingInfo 없음")
                );

            List<Map> parkingInfoDtoList = parkingInfoList.stream()
                .map(o -> Map.builder()
                    .id(o.getId())
                    .parkingStatus(o.getParkingStatus())
                    .build()
                ).collect(Collectors.toList());

            ParkingLotMap result = ParkingLotMap.builder()
                .id(parkingLot.getId())
                .name(parkingLot.getName())
                .total(parkingLot.getTotal())
                .used(parkingLot.getUsed())
                .parkingInfoList(
                    parkingInfoDtoList
                )
                .build();

            return result;

        } catch (Exception e) {
            log.info("[ParkingService] {}", e.toString());

            throw e;
        }

    }


}
