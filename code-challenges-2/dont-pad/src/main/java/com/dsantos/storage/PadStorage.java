package com.dsantos.storage;

import com.dsantos.model.Pad;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class PadStorage implements PadRepository {

    private final Map<String, Pad> pads = new HashMap<>();
    private final Path storageDir = Paths.get("pads");

    public PadStorage() {
        try {
            Files.createDirectories(storageDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory", e);
        }
    }

    @Override
    public String getContent(String padId) {
        if (!pads.containsKey(padId)) {
            loadFromDisk(padId);
        }
        Pad pad = pads.get(padId);
        return pad == null ? "" : pad.getContent();
    }

    @Override
    public void saveContent(String padId, String content) {
        pads.computeIfAbsent(padId, Pad::new).setContent(content);
        saveToDisk(padId, content);
    }

    @Override
    public boolean exists(String padId) {
        return pads.containsKey(padId) || Files.exists(storageDir.resolve(padId + ".txt"));
    }

    private void saveToDisk(String padId, String content) {
        try {
            Files.writeString(storageDir.resolve(padId + ".txt"), content);
        } catch (IOException e) {
            System.err.println("Failed to save pad to disk: " + padId);
        }
    }

    private void loadFromDisk(String padId) {
        Path file = storageDir.resolve(padId + ".txt");
        if (Files.exists(file)) {
            try {
                String content = Files.readString(file);
                Pad pad = new Pad(padId);
                pad.setContent(content);
                pads.put(padId, pad);
            } catch (IOException e) {
                System.err.println("Failed to load pad from disk: " + padId);
            }
        }
    }
}
