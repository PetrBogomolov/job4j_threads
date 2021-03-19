package ru.job4j.multithreading.wait;

import org.junit.Test;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenOffer10ElementAndPoll5ElementThenStay5Element() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final CopyOnWriteArrayList<Integer> copy = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i <= 5; i++) {
                        queue.offer(i);
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            copy.add(queue.poll());
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(copy, is(Arrays.asList(0, 1, 2, 3, 4, 5)));
    }
}
