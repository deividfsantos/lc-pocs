package com.dsantos.storage;

import com.dsantos.model.Pad;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PadStorage {

    private final Map<String, Pad> pads = new HashMap<>();

    public String getContent(String padId) {
        System.out.println("Getting content for pad: " + padId);
        Pad pad = pads.get(padId);
        return pad == null ? "" : pad.getContent();
    }

    public void saveContent(String padId, String content) {
        System.out.println("Saving content for pad: " + padId);
        pads.computeIfAbsent(padId, Pad::new).setContent(content);
    }

    public boolean exists(String padId) {
        return pads.containsKey(padId);
    }
}
