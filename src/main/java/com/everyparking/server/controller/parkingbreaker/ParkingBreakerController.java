package com.everyparking.server.controller.parkingbreaker;

import com.everyparking.server.data.dto.ParkingBreakerDto;
import com.everyparking.server.data.dto.ParkingBreakerDto.Response;
import com.everyparking.server.service.ParkingBreakerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 *
 * /api/vehicle_validation
 * {
 *      "plateNumber": "65노0887"
 * } -> {"isValid": true}
 */

@AllArgsConstructor
@Slf4j
@RequestMapping("/api/parking_breaker")
public class ParkingBreakerController {

    private final ParkingBreakerService parkingBreakerService;


    @PostMapping("/isValid")
    public ResponseEntity<?> isValid(@RequestBody ParkingBreakerDto.Request request) {
        log.info("[ParkingBreakerController] {}", request.toString());

        Response isValid = parkingBreakerService.isValid(request);

        if (isValid.isValid()) {

            return ResponseEntity.status(HttpStatus.OK).body(isValid);
        } else {
//            log.info("[ParkingBreakerController] {}", isValid.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(isValid);
        }
    }

}
