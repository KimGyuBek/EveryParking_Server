package com.everyparking.server.service;

import com.everyparking.server.data.dto.EntryLogDto;
import com.everyparking.server.data.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerWebService {
    Page<EntryLogDto> getAllEntryLogs(Pageable pageable);
    Page<Report> getAllReportLogs(Pageable pageable);

    void violation(String userId);
}