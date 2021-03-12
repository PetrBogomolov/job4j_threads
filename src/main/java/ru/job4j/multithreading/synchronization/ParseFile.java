package ru.job4j.multithreading.synchronization;

import java.io.*;

public class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        StringBuilder output = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            while (read.ready()) {
                output.append(read.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ParseFile parseFile = new ParseFile(new File("test.txt"));
        Thread first = new Thread(
                () -> {
                    parseFile.saveContent("Content for this file");
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(parseFile.getContent());
                }
        );
        first.start();
        second.start();
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.join();
        System.out.println(second.getState());
        second.join();
    }
}
