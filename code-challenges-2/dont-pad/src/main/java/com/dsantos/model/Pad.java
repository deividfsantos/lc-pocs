package com.dsantos.model;

import java.time.Instant;

public class Pad {

    private String id;
    private String content;
    private Instant lastModified;

    public Pad(String id) {
        this.id = id;
        this.content = "";
        this.lastModified = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.lastModified = Instant.now();
    }

    public Instant getLastModified() {
        return lastModified;
    }
}
