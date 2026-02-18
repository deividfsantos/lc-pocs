package com.dsantos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class LocalFileShareService implements FileShareService {
    private final Path baseDir;
    private final CryptoService crypto;
    private final FileRepository repository;

    public LocalFileShareService(Path baseDir, CryptoService crypto, FileRepository repository) {
        this.baseDir = baseDir;
        this.crypto = crypto;
        this.repository = repository;
        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create base storage directory", e);
        }
    }

    @Override
    public UUID save(String originalName, byte[] data) {
        try {
            UUID id = UUID.randomUUID();
            byte[] encrypted = crypto.encrypt(data);
            Path file = baseDir.resolve(id.toString() + ".bin");
            // Write atomically by using CREATE_NEW; if file exists, fail
            Files.write(file, encrypted, StandardOpenOption.CREATE_NEW);
            FileMetadata meta = new FileMetadata(id, originalName, data.length, Instant.now(), file.toString());
            repository.save(meta);
            return id;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    @Override
    public byte[] restore(UUID id) {
        return repository.get(id).map(m -> {
            try {
                Path p = Paths.get(m.path());
                byte[] encrypted = Files.readAllBytes(p);
                return crypto.decrypt(encrypted);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read encrypted file", e);
            }
        }).orElseThrow(() -> new RuntimeException("File not found: " + id));
    }

    @Override
    public boolean delete(UUID id) {
        return repository.get(id).map(m -> {
            try {
                Path p = Paths.get(m.path());
                Files.deleteIfExists(p);
                repository.delete(id);
                return true;
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete file", e);
            }
        }).orElse(false);
    }

    @Override
    public List<FileMetadata> listFiles() {
        return repository.listAll();
    }

    @Override
    public List<FileMetadata> search(String query) {
        return repository.searchByName(query);
    }
}
