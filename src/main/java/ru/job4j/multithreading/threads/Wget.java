package ru.job4j.multithreading.threads;

public class Wget {
    public static void main(String[] args) {
        Thread loading = new Thread(
                () -> {
                    try {
                        int index = 0;
                        while (index <= 100) {
                            System.out.print("\rLoading : " + index + "%");
                            Thread.sleep(1000);
                            index++;
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        loading.start();
    }
}
