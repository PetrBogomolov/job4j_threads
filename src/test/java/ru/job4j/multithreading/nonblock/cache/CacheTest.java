package ru.job4j.multithreading.nonblock.cache;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    private Cache cache;
    private Base original;
    private Base modified;
    private Base newModel;

    @Before
    public void setup() {
        cache = new Cache();
        original = new Base(1, 1);
        modified = new Base(1, 1);
        modified.setName("add name");
        newModel = new Base(1, 2);
    }

    @Test
    public void whenAddSuccessful() {
        assertThat(cache.add(original), is(true));
    }

    @Test
    public void whenUpdateSuccessfulThenVersionModelPlusOne() {
        cache.add(original);
        cache.update(modified);
        assertThat(cache.getBase(1).getVersion(), is(2));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateModelWithNotEqualsVersionsThenOptimisticException() {
        cache.add(original);
        cache.update(newModel);
    }

    @Test
    public void whenDeleteSuccessfulThenTrue() {
        cache.add(original);
        assertThat(cache.delete(original), is(true));
    }

    @Test
    public void whenDeleteNonexistentModelThenFalse() {
        assertThat(cache.delete(original), is(false));
    }
}
