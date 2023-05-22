package com.everyparking.server.controller.web;

import com.everyparking.server.data.dto.EntryLogDto;
import com.everyparking.server.data.dto.ParkingDto;
import com.everyparking.server.data.entity.ParkingLot;
import com.everyparking.server.service.ManagerWebService;
import com.everyparking.server.service.ParkingBreakerService;
import com.everyparking.server.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
public class ManagerWebController {
    private final ManagerWebService managerWebService;
    private final ParkingBreakerService parkingBreakerService;
    private final ParkingService parkingService;

    @GetMapping("/api/entry-log")
    public List<EntryLogDto> getEntryLogs(@PageableDefault(size=20) Pageable pageable) {
        return managerWebService.getAllLogs(pageable).getContent();
    }

    @GetMapping("/api/exit")
    public ResponseEntity<?> setExitLog(@RequestParam("number") String carNumber) {
        try {
            parkingBreakerService.exit(carNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/map/{parkingLotId}")
    public ResponseEntity<ParkingDto.ParkingLotMap> parkingLotMap(@PathVariable Long parkingLotId) {
        try {
            /*ParkingLot 조회*/
            ParkingLot parkingLot = parkingService.findParkingLotByParkingLotId(
                    parkingLotId);
            ParkingDto.ParkingLotMap parkingLotMap = parkingService.findParkingLotMap(parkingLot);

            return status(HttpStatus.OK)
                    .body(parkingLotMap);

        } catch (Exception e) {
            return status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
