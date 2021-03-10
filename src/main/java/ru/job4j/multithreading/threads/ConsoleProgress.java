package ru.job4j.multithreading.threads;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = {'\\', '|', '/'};
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                if (i == process.length) {
                    i = 0;
                }
                System.out.print("\rLoading: " + process[i++]);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ConsoleProgress(), "interruption");
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        System.out.println(thread.getState());
    }
}
