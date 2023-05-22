package com.everyparking.server.service.impl;

import com.everyparking.server.data.dto.EntryLogDto;
import com.everyparking.server.data.entity.EntryLog;
import com.everyparking.server.data.repository.EntryLogRepository;
import com.everyparking.server.service.ManagerWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagerWebServiceImpl implements ManagerWebService {
    private final EntryLogRepository entryLogRepository;

    @Override
    public Page<EntryLogDto> getAllLogs(Pageable pageable) {
        Page<EntryLogDto> entryLogs = entryLogRepository.findAllByOrderByEntryTimeDesc(pageable).map(EntryLog::toDto);
        return entryLogs;
    }
}
