package com.everyparking.server.event;

import com.everyparking.server.data.dto.EntryLogDto;
import com.everyparking.server.data.entity.EntryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class EntryLogEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public EntryLogEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleEntityChangeEvent(EntryLogChangeEvent event) {
        EntryLogDto changedEntity = (EntryLogDto) event.getSource();
        messagingTemplate.convertAndSend("/topic/entry-log", changedEntity);
    }

}
