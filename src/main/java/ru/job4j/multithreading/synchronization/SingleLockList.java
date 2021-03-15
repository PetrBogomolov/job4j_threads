package ru.job4j.multithreading.synchronization;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final SimpleArray<T> array;

    public SingleLockList() {
        array = new SimpleArray<>();
    }

    public synchronized void add(T value) {
        array.add(value);
    }

    public synchronized T get(int index) {
        return array.get(index);
    }

    private SimpleArray<T> copy(SimpleArray<T> simpleArray) {
        SimpleArray<T> copy = new SimpleArray<>();
        simpleArray.iterator().forEachRemaining(copy::add);
        return copy;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.array).iterator();
    }
}
