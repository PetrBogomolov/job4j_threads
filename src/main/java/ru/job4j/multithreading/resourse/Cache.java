package ru.job4j.multithreading.resourse;

public final class Cache {

    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
