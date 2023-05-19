package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.ParkingBreakerDto;
import com.everyparking.server.data.dto.ParkingBreakerDto.Request;
import com.everyparking.server.data.entity.Car;
import com.everyparking.server.data.repository.CarRepository;
import com.everyparking.server.data.repository.ParkingInfoRepository;
import com.everyparking.server.exception.CarValidationException;
import com.everyparking.server.service.ParkingBreakerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional()
public class ParkingBreakerServiceImpl implements ParkingBreakerService {

    private final ParkingInfoRepository parkingInfoRepository;

    private final CarRepository carRepository;

    @Override
    public ParkingBreakerDto.Response isValid(Request request) {
        String plateNumber = request.getPlateNumber();
        int parkingLotId = request.getParkingLotId();

        try {
            /*출입 가능 차량 조회*/
            Car findCar = carRepository.findByCarNumber(plateNumber).orElseThrow(
                () -> new CarValidationException("미등록 차량")
            );

            if (!findCar.getMember().checkMemberStatus()) {
                throw new Exception("위약된 사용자");
            }

            /*TODO 위약 검증 로직 추가*/

            /* 주차장 출입 처리*/
            try {
                findCar.setEnter(parkingLotId);
                carRepository.save(findCar);

            } catch (Exception e) {
                log.debug("[ParkingBreakerService] {}", e.toString());

            }

            log.info("[ParkingBreakerService] {} 등록 차량", plateNumber);
            log.info("[ParkingBreakerService] {}, {}번 주차장 출입", findCar.getCarNumber(),
                findCar.getCarEnterStatus().getParkingLotId());

            log.debug("[ParkingBreakerService] {}", findCar.getCarEnterStatus().toString());
//            log.debug("[{}]", this.getClass().getName().toString());

            return ParkingBreakerDto.Response
                .builder()
                .valid(true)
                .build();

        } catch (CarValidationException e) {
            log.debug("[ParkingBreakerService] {}", e.toString());
            log.info("[ParkingBreakerService] {} 미등록 차량", plateNumber);
            return ParkingBreakerDto.Response
                .builder()
                .valid(false)
                .build();
        } catch (Exception e) {
            log.debug("[ParkingBreakerService] {}", e.toString());
            log.info("[ParkingBreakerService] {} 위약처리된 차량", plateNumber);
            return ParkingBreakerDto.Response
                .builder()
                .valid(false)
                .build();
        }

    }

}