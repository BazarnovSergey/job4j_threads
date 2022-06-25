package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    public final static long MILLISECONDS = 1000;
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long totalBytes = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
                if (totalBytes >= speed) {
                    long time = System.currentTimeMillis() - start;
                    if (time <= 1000) {
                        Thread.sleep(MILLISECONDS - time);
                    }
                    totalBytes = 0;
                    start = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void checkValidateArgs(String[] args) {
        if (args.length < 3) {
            System.out.println("Check the correctness of the arguments."
                    + "\nEnter 3 parameters: url, speed and file name to save");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        checkValidateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}