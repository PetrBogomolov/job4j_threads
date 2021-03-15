package ru.job4j.multithreading.synchronization;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] container;
    private int possition = 0;
    private int modCount = 0;

    public SimpleArray() {
        container = new Object[10];
    }

    public T get(int index) {
        int indexOf = Objects.checkIndex(index, possition);
        return (T) container[indexOf];
    }

    public void add(T model) {
        container[possition++] = model;
        if (possition >= container.length) {
            container = grow();
        }
        modCount++;
    }

    private Object[] grow() {
        return Arrays.copyOf(container, container.length * 2);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            private int expModCount = modCount;

            @Override
            public boolean hasNext() {
                return index < possition;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return (T) container[index++];
            }
        };
    }
}
