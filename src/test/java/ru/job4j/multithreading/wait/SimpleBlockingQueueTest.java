package ru.job4j.multithreading.wait;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);

    @Test
    public void whenOffer10ElementAndPoll5ElementThenStay5Element() throws InterruptedException {
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i <= 10; i++) {
                        queue.offer(i);
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    int i = 0;
                    while (i <= 5) {
                        queue.poll();
                        i++;
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.getSize(), is(5));
    }
}
