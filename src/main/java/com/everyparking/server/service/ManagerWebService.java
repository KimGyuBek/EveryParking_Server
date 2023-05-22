package com.everyparking.server.service;

import com.everyparking.server.data.dto.EntryLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerWebService {
    Page<EntryLogDto> getAllLogs(Pageable pageable);
}