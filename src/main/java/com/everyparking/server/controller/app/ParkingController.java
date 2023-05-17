package com.everyparking.server.controller.app;

import static org.springframework.http.ResponseEntity.status;

import com.everyparking.server.data.dto.ParkingDto;
import com.everyparking.server.data.dto.ParkingDto.MyParkingStatus;
import com.everyparking.server.data.dto.ParkingDto.ParkingLotMap;
import com.everyparking.server.data.entity.ParkingLot;
import com.everyparking.server.service.ParkingService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j

@RequestMapping("/app/parking")
public class ParkingController {

    private final ParkingService parkingService;


    /**
     * 메인화면 - 내 주차 정보
     *
     * @param request
     * @return
     */
    @GetMapping("/myParkingStatus")
    public ResponseEntity<?> myParkingStatus(HttpServletRequest request) {
        String userId = request.getHeader("userId").toString();
//        String userId = request.getHeaders().getFirst("userId");
        log.info("[ParkingController] userId : {}", userId);

        try {
            MyParkingStatus result = parkingService.findByUserId(userId);
            log.info("[{}] {}", this.getClass().getName(), result.toString());

            return status(HttpStatus.OK)
                .body(result);

        } catch (Exception e) {
//            log.info("[ParkingController] {}", e.getCause());
            log.info("[ParkingController] {}", e.getMessage());
            return status(HttpStatus.NOT_FOUND).build();
        }
    }


    /**
     * 주차 맵
     */
    @GetMapping("/map/{parkingLotId}")
    public ResponseEntity<ParkingDto.ParkingLotMap> parkingLotMap(@PathVariable Long parkingLotId) {

        log.info("[ParkingController] parkingLotId : {}", parkingLotId);

        try {
            /*ParkingLot 조회*/
            ParkingLot parkingLot = parkingService.findParkingLotByParkingLotId(
                parkingLotId);
            ParkingLotMap parkingLotMap = parkingService.findParkingLotMap(parkingLot);

            return status(HttpStatus.OK)
                .body(parkingLotMap);

        } catch (Exception e) {
            log.info("[ParkingController] {}", e.toString());

            return status(HttpStatus.NOT_FOUND)
                .build();
        }
    }

    /**
     * 자리 정보
     *
     * @param parkingInfoId
     * @return
     */
    @GetMapping("/info/{parkingInfoId}")
    public ResponseEntity<?> parkingInfo(@PathVariable Long parkingInfoId) {

        try {
            return status(HttpStatus.OK)
                .body(
                    parkingService.findByParingId(parkingInfoId));
        } catch (Exception e) {
            log.info("[{}] {}", this.getClass().getName(), e.getMessage());

            return status(HttpStatus.BAD_REQUEST)
                .build();
        }
    }

    @GetMapping("/assign/{parkingInfoId}")
    public ResponseEntity<?> assign(@PathVariable Long parkingInfoId, HttpServletRequest request) {
        String userId = request.getHeader("userId");

        try {
            return status(HttpStatus.OK)
                .body(
                    parkingService.assign(parkingInfoId, userId));

        } catch (Exception e) {
            log.info("[{}] {}", this.getClass().getName(), e.getMessage());

            return status(HttpStatus.NOT_FOUND).build();

        }

    }

//    @GetMapping("/return")
//    public ResponseEntity<?>


}
