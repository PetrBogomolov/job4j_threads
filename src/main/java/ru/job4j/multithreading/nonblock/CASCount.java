package ru.job4j.multithreading.nonblock;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer currentValue;
        int newValue;
        do {
            currentValue = count.get();
            newValue = currentValue + 1;
        } while (!count.compareAndSet(currentValue, newValue));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount cas = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        cas.increment();
                        System.out.println(cas.get());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        cas.increment();
                        System.out.println(cas.get());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        first.start();
        second.start();
    }
}
