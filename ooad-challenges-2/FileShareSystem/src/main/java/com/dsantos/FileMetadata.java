package com.dsantos;

import java.time.Instant;
import java.util.UUID;

public record FileMetadata(UUID id, String originalName, long size, Instant createdAt, String path) {

    @Override
    public String toString() {
        return "FileMetadata{" +
                "id=" + id +
                ", originalName='" + originalName + '\'' +
                ", size=" + size +
                ", createdAt=" + createdAt +
                ", path='" + path + '\'' +
                '}';
    }
}
