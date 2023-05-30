package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userDao = new UserServiceImpl();
        userDao.createUsersTable();
        userDao.saveUser("Ruslan1", "Samatov", (byte) 25);
        userDao.saveUser("Ruslan2", "Samatov", (byte) 25);
        userDao.saveUser("Ruslan3", "Samatov", (byte) 25);
        userDao.saveUser("Ruslan4", "Samatov", (byte) 25);
        userDao.removeUserById(3);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

    }
}
