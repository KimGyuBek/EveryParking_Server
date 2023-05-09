package com.everyparking.server.controller.app;

import com.everyparking.server.data.dto.ParkingDto;
import com.everyparking.server.service.ParkingService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @param request
     * @return
     */
    @GetMapping("/myParkingStatus")
    public ResponseEntity<?> myParkingStatus(HttpServletRequest request) {
        String userId = request.getHeader("userId").toString();
//        String userId = request.getHeaders().getFirst("userId");
        log.info("[ParkingController] userId : {}", userId);

        try {
            ParkingDto.MyParkingStatus result = parkingService.findByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (Exception e) {
            log.info("[ParkingController] {}", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
