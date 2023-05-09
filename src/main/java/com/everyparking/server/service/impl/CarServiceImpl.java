package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.CarDto;
import com.everyparking.server.data.entity.Car;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.repository.CarRepository;
import com.everyparking.server.data.repository.MemberRepository;
import com.everyparking.server.exception.CarException;
import com.everyparking.server.exception.UserNotFoundException;
import com.everyparking.server.service.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final MemberRepository memberRepository;

    @Override
    public CarDto.Register register(CarDto.Register register, String userId) {

        try {
            /*사용자 조회*/
            Member findMember = memberRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException("사용자를 찾을 수 없음")
            );

            try {
                /*차량 저장*/
                Car car = register.toEntity(register, findMember);
                log.info("[CarService] {}", car.toString());
                carRepository.save(car);

                log.info("[CarService] {} 차량 등록 완료", userId);

                return register;

            } catch (Exception e) {
                log.info("[CarService] {}", e.toString());
            }

        } catch (UserNotFoundException e) {
            log.info("[CarService] {}", e.toString());
        } catch (Exception e) {
            log.info("[CarService] {}", e.toString());
        }

        throw new CarException("차량 등록 실패");

    }
}
