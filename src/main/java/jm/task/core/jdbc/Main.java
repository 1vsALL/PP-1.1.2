package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();

        userService.saveUser("One", "lastOne", (byte) 1);
        userService.saveUser("Second", "lastSecond", (byte) 2);
        userService.saveUser("Third", "lastThird", (byte) 3);
        userService.saveUser("Fourth", "lastFourth", (byte) 4);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
