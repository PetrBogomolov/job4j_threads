package ru.job4j.multithreading.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {

    private final int total;
    @GuardedBy("this")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        this.notifyAll();
    }

    public synchronized void await() {
        while (count != total) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(15);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 20; i++) {
                        countBarrier.count();
                    }
                }
        );
        Thread second = new Thread(countBarrier::await);
        first.start();
        second.start();
        System.out.println(first.getState());
        System.out.println(second.getState());
    }
}
