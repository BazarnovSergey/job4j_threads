package ru.job4j.userstorage;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest {

    private static class ThreadTransfer extends Thread {
        private final UserStorage userStorage;

        private ThreadTransfer(UserStorage userStorage) {
            this.userStorage = userStorage;
        }

        @Override
        public void run() {
            this.userStorage.transfer(1, 2, 10);
        }
    }

    @Test
    public void whenThread1TransferUser1ToUser2() throws InterruptedException {
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        userList.add(user1);
        userList.add(user2);
        UserStorage userStorage = new UserStorage(userList);
        Thread thread1 = new ThreadTransfer(userStorage);
        Thread thread2 = new ThreadTransfer(userStorage);
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        assertThat(user1.getAmount(), is(80));
        assertThat(user2.getAmount(), is(220));
    }
}