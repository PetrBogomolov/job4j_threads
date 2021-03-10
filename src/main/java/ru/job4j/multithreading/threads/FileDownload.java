package ru.job4j.multithreading.threads;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload implements Runnable {

    private final String url;
    private final int speed;

    public FileDownload(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fos = new FileOutputStream("pom_tpm.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long timeStartLoading = System.currentTimeMillis();
            long timeLoading;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                fos.write(dataBuffer, 0, bytesRead);
                timeLoading = System.currentTimeMillis() - timeStartLoading;
                if (timeLoading < speed) {
                    Thread.sleep(speed - timeLoading);
                }
                timeStartLoading = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = "https://github.com/PetrBogomolov/job4j_threads/blob/master/pom.xml";
        int speed = 1000;
        Thread wget = new Thread(new FileDownload(url, speed));
        wget.start();
        wget.join();
    }
}
