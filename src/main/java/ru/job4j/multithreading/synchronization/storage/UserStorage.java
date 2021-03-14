package ru.job4j.multithreading.synchronization.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.*;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add(User user) {
        return storage.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return storage.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User payer = storage.get(fromId);
        User recipient = storage.get(toId);
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
}
