package ru.job4j.multithreading.synchronization.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.*;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Set<User> storage = new TreeSet<>(Comparator.comparing(User::getId));

    public synchronized boolean add(User user) {
        return storage.add(user);
    }

    public synchronized boolean update(User user) {
        if (delete(user)) {
            add(user);
            return true;
        } else {
            System.out.println("User was not found");
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User payer = null;
        User recipient = null;
        for (User user : storage) {
            if (user.getId() == fromId) {
                payer = user;
            }
            if (user.getId() == toId) {
                recipient = user;
            }
        }
        if (payer != null && recipient != null) {
            if (payer.getAmount() > amount) {
                payer.setAmount(payer.getAmount() - amount);
                recipient.setAmount(recipient.getAmount() + amount);
            } else {
                System.out.println("There are not enough funds in your account");
            }
        } else {
            System.out.println("Payer and Recipient were not found");
        }
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        User payer = new User(1, 1000);
        User recipient = new User(2, 1000);
        storage.add(payer);
        storage.add(recipient);
        storage.storage.forEach(System.out::println);
        storage.transfer(1, 2, 750);
        storage.storage.forEach(System.out::println);
    }
}
