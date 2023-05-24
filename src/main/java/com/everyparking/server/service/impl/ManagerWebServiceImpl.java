package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.EntryLogDto;
import com.everyparking.server.data.entity.*;
import com.everyparking.server.data.repository.*;
import com.everyparking.server.event.EntryLogChangeEvent;
import com.everyparking.server.service.ManagerWebService;
import com.everyparking.server.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagerWebServiceImpl implements ManagerWebService {
    private final EntryLogRepository entryLogRepository;
    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;
    private final CarRepository carRepository;
    private final ParkingService parkingService;
    private final ParkingInfoRepository parkingInfoRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Page<EntryLogDto> getAllEntryLogs(Pageable pageable) {
        Page<EntryLogDto> entryLogs = entryLogRepository.findAllByOrderByEntryTimeDesc(pageable).map(EntryLog::toDto);
        return entryLogs;
    }

    public Page<Report> getAllReportLogs(Pageable pageable) {
        // TODO: 이미지도 불러와야됨.
        return  reportRepository.findAllByOrderByCreatedTimeDesc(pageable);
    }

    @Override
    public void violation(String userId) {
        try {
            Optional<Member> found = memberRepository.findByUserId(userId);
            if (found.isEmpty()) {
                throw new Exception("회원이 존재하지 않습니다.");
            }
            /*회원 상태 정보 갱신 -> FORBIDDEN*/
            Member updated = found.get();
            updated.setMemberStatus(MemberStatus.FORBIDDEN);
            memberRepository.save(updated);

            // car is_entered 업데이트
            // Car 엔티티 @Setter 추가
            Optional<Car> found3 = carRepository.findById(found.get().getCar().getId());
            if (found3.isEmpty()) {
                throw new Exception("error");
            }
            Car updated3 = found3.get();
            updated3.setCarEnterStatus(new CarEnterStatus(-1, false));
            carRepository.save(updated3);

            Optional<EntryLog> found2 = entryLogRepository.findFirstByCarNumberAndExitTimeIsNull(found3.get().getCarNumber());
            if(found2.isEmpty()) {
                throw new Exception("들어온 기록이 없음.");
            }

            EntryLog updated2 = found2.get();
            ZoneId zoneId = ZoneId.of("Asia/Seoul");
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
            updated2.setExitTime(zonedDateTime.toLocalDateTime());
            entryLogRepository.save(updated2);
            /*관리자 페이지 출차 기록*/
            eventPublisher.publishEvent(new EntryLogChangeEvent(updated2.toDto()));

            /*member parking info update*/
            Optional<Member> member = memberRepository.findByCarId(updated3.getId());
            if (member.isPresent()) {
                Member foundMember = member.get();
                Long parkingInfoId = foundMember.getParkingInfo().getId();
                if (parkingInfoId != null) {
                    foundMember.changeParkingStatus(parkingInfoRepository.findById(foundMember.getParkingInfo().getId()).get());
                    memberRepository.save(foundMember);
                    /*관리자 실시간 맵 렌더링*/
                    eventPublisher.publishEvent(new EntryLogChangeEvent(parkingService.findByParingId(parkingInfoId)));
                }
            }


        } catch (Exception e) {

        }
    }
}
