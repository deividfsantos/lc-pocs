package com.dsantos;

import java.io.IOException;
import java.util.List;

public interface Storage {
    void save(List<Note> notes) throws IOException;
    List<Note> load() throws IOException;
}

