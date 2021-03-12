package ru.job4j.multithreading.resourse;

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
}
