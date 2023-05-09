package com.everyparking.server;

import com.everyparking.server.data.entity.Car;
import com.everyparking.server.data.entity.CarEnterStatus;
import com.everyparking.server.data.entity.CarStatus;
import com.everyparking.server.data.entity.ParkingInfo;
import com.everyparking.server.data.entity.ParkingLot;
import com.everyparking.server.data.entity.ParkingStatus;
import com.everyparking.server.data.repository.CarRepository;
import com.everyparking.server.data.repository.MemberRepository;
import com.everyparking.server.data.repository.MessageRepository;
import com.everyparking.server.data.repository.ParkingInfoRepository;
import com.everyparking.server.data.repository.ParkingLotRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final MemberRepository memberRepository;

    private final MessageRepository messageRepository;

    private final CarRepository carRepository;

    private final ParkingInfoRepository parkingInfoRepository;

    private final ParkingLotRepository parkingLotRepository;


    @PostConstruct
    private void initData() {
        initCar();

        initParkingInfo();

        initParkingLot();



    }

    private void initParkingInfo() {
        for (int i = 1; i <= 20; i++) {
            parkingInfoRepository.save(
                ParkingInfo
                    .builder()
                    .parkingId(i)
                    .parkingStatus(ParkingStatus.AVAILABLE)
                    .build());
        }
    }

    private void initParkingLot() {
        ParkingLot parkingLot = ParkingLot
            .builder()
            .name("테스트")
            .total(20)
            .used(0)
            .available(20 - 0)
            .build();

        parkingLotRepository.save(parkingLot);
    }

    private void initCar() {
        Car car1 = makeCar("65노0887", "Sonata");
        Car car2 = makeCar("38육4104", "Grandeur");
        Car car3 = makeCar("245우1234", "Santafe");
        Car car4 = makeCar("68오8269", "Genesis");

        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        carRepository.save(car4);
    }

    private static Car makeCar(String carNumber, String modelName) {
        Car car = Car.builder()
            .carNumber(carNumber)
            .modelName(modelName)
            .carStatus(CarStatus.APPROVED)
            .carEnterStatus(new CarEnterStatus(-1, false))
            .build();
        return car;
    }


}
