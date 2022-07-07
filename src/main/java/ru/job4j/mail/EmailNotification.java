package ru.job4j.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(
                new Thread(
                        () -> {
                            send(subject(user), body(user), user.getEmail());
                        }
                )
        );
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }

    private String subject(User user) {
        return "Notification  "
                + user.getUsername()
                + " to email "
                + user.getEmail();
    }

    private String body(User user) {
        return "Add a new event to " + user.getUsername();
    }

}
