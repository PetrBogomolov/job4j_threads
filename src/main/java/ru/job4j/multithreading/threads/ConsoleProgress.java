package ru.job4j.multithreading.threads;

public class ConsoleProgress implements Runnable {
    private final char[] process = {'\\', '|', '/'};

    @Override
    public void run() {
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
            e.printStackTrace();
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
