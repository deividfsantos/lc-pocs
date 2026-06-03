package com.dsantos.storage;

public interface PadRepository {

    String getContent(String padId);

    void saveContent(String padId, String content);

    boolean exists(String padId);
}
