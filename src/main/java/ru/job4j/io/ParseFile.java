package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return content(x -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(x -> x < 0x80);
    }

    public String content(Predicate<Character> filter) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = in.read()) != 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}