package com.dsantos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteFileStorage {

    private static final String DELIMITER = "|";
    private static final String ESCAPED_DELIMITER = "\\|";
    private final String filePath;

    public NoteFileStorage(String filePath) {
        this.filePath = filePath;
    }

    public void save(List<Note> notes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Note note : notes) {
                writer.write(encode(note));
                writer.newLine();
            }
        }
    }

    public List<Note> load() throws IOException {
        List<Note> notes = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return notes;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Note note = decode(line);
                if (note != null) notes.add(note);
            }
        }
        return notes;
    }

    private String encode(Note note) {
        return note.getId()
                + DELIMITER + note.getTitle().replace(DELIMITER, "")
                + DELIMITER + note.getContent().replace(DELIMITER, "")
                + DELIMITER + note.getUpdatedAt();
    }

    private Note decode(String line) {
        String[] parts = line.split(ESCAPED_DELIMITER, 4);
        if (parts.length < 4) return null;
        return new Note(parts[0], parts[1], parts[2]);
    }
}

