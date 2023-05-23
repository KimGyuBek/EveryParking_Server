package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.EntryLogDto;
import com.everyparking.server.data.dto.ReportDto;
import com.everyparking.server.data.dto.ViolationDto;
import com.everyparking.server.data.entity.EntryLog;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.entity.MemberStatus;
import com.everyparking.server.data.entity.Report;
import com.everyparking.server.data.repository.EntryLogRepository;
import com.everyparking.server.data.repository.MemberRepository;
import com.everyparking.server.data.repository.ReportRepository;
import com.everyparking.server.service.ManagerWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagerWebServiceImpl implements ManagerWebService {
    private final EntryLogRepository entryLogRepository;
    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

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
    public void violation(ViolationDto violationDto) {
        try {
            Optional<Member> found = memberRepository.findByUserId(violationDto.getUserId());
            if (found.isEmpty()) {
                throw new Exception("회원이 존재하지 않습니다.");
            }
            Member updated = found.get();
            updated.setMemberStatus(MemberStatus.FORBIDDEN);
            memberRepository.save(updated);

        } catch (Exception e) {

        }
    }
}
