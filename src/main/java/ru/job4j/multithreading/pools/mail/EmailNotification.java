package ru.job4j.multithreading.pools.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format(
                    "Notification %s, to email %s", user.getUsername(), user.getEmail());
            String body = String.format(
                    "Add a new event to %s", user.getUsername());
            send(subject, body, user.getEmail());
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {
        System.out.printf("send message: \"%s\", to %s%n", body, email);
    }

    public static void main(String[] args) {
        EmailNotification mailing = new EmailNotification();
        for (int i = 0; i <= 100; i++) {
            mailing.emailTo(new User(String.format("user_%s", i), String.format("email_%s", i)));
        }
        mailing.close();
    }
}
