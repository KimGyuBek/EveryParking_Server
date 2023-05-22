package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.ReportDto;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.entity.Report;
import com.everyparking.server.data.repository.MemberRepository;
import com.everyparking.server.data.repository.ReportRepository;
import com.everyparking.server.data.repository.UploadFileRepository;
import com.everyparking.server.exception.UserNotFoundException;
import com.everyparking.server.filestore.FileStore;
import com.everyparking.server.filestore.UploadFile;
import com.everyparking.server.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;


    private final FileStore fileStore;

    @Override
    public void uploadReport(String userId, ReportDto reportDto, MultipartFile multipartFile) {

        try {

            Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException("사용자 없음"));

            Report report = reportDto.toEntity(member);

            reportRepository.save(report);

            member.saveReport(report);
            UploadFile uploadFile = fileStore.storeFile(multipartFile, userId);
            report.uploadFile(uploadFile);



        } catch (Exception e) {
            log.info("[{}] {}", this.getClass().getName(),
                e.getMessage());

        }


    }


}

