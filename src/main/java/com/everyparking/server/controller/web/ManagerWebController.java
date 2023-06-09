package com.everyparking.server.controller.web;

import com.everyparking.server.data.dto.*;
import com.everyparking.server.data.entity.ParkingLot;
import com.everyparking.server.data.entity.ParkingStatus;
import com.everyparking.server.data.entity.Report;
import com.everyparking.server.service.ManagerWebService;
import com.everyparking.server.service.ParkingBreakerService;
import com.everyparking.server.service.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ManagerWebController {
    private final ManagerWebService managerWebService;
    private final ParkingBreakerService parkingBreakerService;
    private final ParkingService parkingService;

    @GetMapping("/api/entry-log")
    public List<EntryLogDto> getEntryLogs(@PageableDefault(size=20) Pageable pageable) {
        return managerWebService.getAllEntryLogs(pageable).getContent();
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

    @GetMapping("/api/map/{parkingLotId}")
    public ResponseEntity<List<ParkingDto.ParkingInfoDto.Info>> parkingLotMap(@PathVariable Long parkingLotId) {
        try {
            /*ParkingLot 조회*/
            ParkingLot parkingLot = parkingService.findParkingLotByParkingLotId(
                    parkingLotId);
            ParkingDto.ParkingLotMap parkingLotMap = parkingService.findParkingLotMap(parkingLot);

            /*각 좌석마다 차량정보와 차량정보*/
            List<ParkingDto.ParkingInfoDto.Info> assignInfo = new ArrayList<>();
            parkingLotMap.getParkingInfoList().forEach(info -> {
                assignInfo.add(parkingService.findByParingId(info.getId()));
            });

            return status(HttpStatus.OK)
                    .body(assignInfo);

        } catch (Exception e) {
            return status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    /**
     * 신고리스트 전체 불러오기. 최근 시간 순으로 정렬
     */
    @GetMapping("/api/report-log")
    public List<Report> getReportLogs(@PageableDefault(size=20) Pageable pageable) {
        return managerWebService.getAllReportLogs(pageable).getContent();
    }

    /**
     * 관리자 전용 회원 위약 처리
     */
    @GetMapping("/api/violation")
    public ResponseEntity<?> violation(HttpServletRequest request) {
        try {
            String userId = request.getHeader("userId");
            if (userId == null) {
                throw new Exception("해당 정보로 위약할 수 없습니다.");
            }
            managerWebService.violation(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User violated Successfully!");
        } catch (Exception e) {
            log.info("[ManagerWebController] {}", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to violate user");
        }
    }
}
