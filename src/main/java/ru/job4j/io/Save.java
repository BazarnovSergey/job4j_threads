package ru.job4j.io;

import java.io.*;

public final class Save {

    private final File file;

    public Save(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
