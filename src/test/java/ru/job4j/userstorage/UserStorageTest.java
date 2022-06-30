package ru.job4j.userstorage;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest {

    @Test
    public void whenTwoThreadsTransferUser1ToUser2() throws InterruptedException {

        UserStorage userStorage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        userStorage.add(user1);
        userStorage.add(user2);

        Thread thread1 = new Thread(
                () -> {
                    userStorage.transfer(1, 2, 10);
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    userStorage.transfer(1, 2, 10);
                }
        );
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        assertThat(user1.getAmount(), is(80));
        assertThat(user2.getAmount(), is(220));
    }
}