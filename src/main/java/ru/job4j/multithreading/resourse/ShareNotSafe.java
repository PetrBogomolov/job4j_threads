package ru.job4j.multithreading.resourse;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        User user2 = User.of("new name");
        cache.add(user);
        cache.add(user2);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                    user2.setName("renew name");
                }
        );
        first.start();
        first.join();
        for (User element : cache.findAll()) {
            System.out.println(element.getName());
        }
    }

    static class User {
        private int id;
        private String name;

        public static User of(String name) {
            User user = new User();
            user.name = name;
            return user;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User: "
                  +  "id = " + id
                  +  ", name = " + name;
        }
    }

    static class UserCache {
        private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
        private final AtomicInteger id = new AtomicInteger();

        public void add(User user) {
            users.put(id.incrementAndGet(), User.of(user.getName()));
        }

        public User findById(int id) {
            return User.of(users.get(id).getName());
        }

        public List<User> findAll() {
            return users.values().stream()
                    .map(user -> User.of(user.getName()))
                    .collect(Collectors.toList());
        }
    }
}
