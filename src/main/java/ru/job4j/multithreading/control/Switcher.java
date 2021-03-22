package ru.job4j.multithreading.control;

public class Switcher {
    private boolean flag = true;

    public synchronized void workA() {
        while (flag) {
            try {
                System.out.println("Thread A");
                Thread.sleep(1000);
                flag = false;
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void workB() {
        while (!flag) {
            try {
                System.out.println("Thread B");
                Thread.sleep(1000);
                flag = true;
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Switcher switcher = new Switcher();
        Thread first = new Thread(
                () -> {
                    while (true) {
                        switcher.workA();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        switcher.workB();
                    }
                }
        );
        first.start();
        second.start();
    }
}
