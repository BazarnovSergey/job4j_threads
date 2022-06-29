package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage implements Storage {

    @GuardedBy("this")
    private final List<User> userList;

    public UserStorage(List<User> users) {
        synchronized (UserStorage.class) {
            this.userList = new ArrayList<>(users);
        }
    }

    @Override
    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!userList.contains(user)) {
            userList.add(new User(user.getId(), user.getAmount()));
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (userList.get(user.getId() - 1).getId() == user.getId()) {
            userList.set(user.getId(), new User(user.getId(), user.getAmount()));
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean delete(User user) {
        return userList.remove(user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User userFrom = this.userList.stream()
                .filter(userFirst -> userFirst.getId() == fromId)
                .findFirst().orElse(null);
        User userTo = this.userList.stream()
                .filter(userSecond -> userSecond.getId() == toId)
                .findFirst().orElse(null);
        if (userFrom != null && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
        }
    }
}