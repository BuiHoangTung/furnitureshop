package com.myproject.furnitureshop.transactions.listener;

import com.myproject.furnitureshop.service.FileStorageService;
import com.myproject.furnitureshop.transactions.event.FileDeleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class FileDeleteListener {
    private final FileStorageService fileStorageService;

    public FileDeleteListener(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Async("fileExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deleteFile(FileDeleteEvent fileDeleteEvent) {
        if (fileDeleteEvent.fileName() == null) {
            return;
        }

        try {
            this.fileStorageService.deleteFile(fileDeleteEvent.fileName());
        } catch(Exception e) {
            log.warn("Failed to delete old file: {}", fileDeleteEvent.fileName(), e);
        }
    }
}
