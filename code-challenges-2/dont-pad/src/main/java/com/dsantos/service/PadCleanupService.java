package com.dsantos.service;

import com.dsantos.storage.PadStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PadCleanupService {

    private final PadStorage padStorage;

    @Autowired
    public PadCleanupService(PadStorage padStorage) {
        this.padStorage = padStorage;
    }

    @Scheduled(fixedRate = 3600000)
    public void removeExpiredPads() {
        padStorage.removeExpired();
    }
}
