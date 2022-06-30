package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage implements Storage {

    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> userList = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean add(User user) {
        return userList.putIfAbsent(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean update(User user) {
        return userList.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return userList.remove(user.getId()) != null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User userFrom = userList.get(fromId);
        User userTo = userList.get(toId);
        if (userFrom != null && userFrom.getAmount() >= amount) {
            userList.get(fromId).setAmount(userFrom.getAmount() - amount);
            userList.get(toId).setAmount(userTo.getAmount() + amount);
        }
    }
}
