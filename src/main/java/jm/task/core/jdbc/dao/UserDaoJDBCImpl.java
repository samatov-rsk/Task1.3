package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users" +
                "(id INTEGER NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(100) not null, " +
                "lastname VARCHAR(100) not null, " +
                "age INTEGER (128) not null, " +
                "PRIMARY KEY(id))";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(createUsersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drobUsersTable = "DROP TABLE IF EXISTS users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(drobUsersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        String save = "INSERT INTO USERSTABLE (name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(save)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeUser = "DELETE FROM users WHERE id=?";
        User user = new User(id);

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(removeUser)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllQuery = "SELECT id, name, lastname, age from users";
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllQuery);
            while (resultSet.next()) { // Оставил создание юзера потому что не подобрал другой локиги. Название и тип возращаемого значения задавала Ката.
                // поэтому я создал юзера, Пока есть значения в таблице == true добавил в Юзера значение с таблицы и добавил этого Юзера в лист. Вернул лист.
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsers = "TRUNCATE USERSTABLE";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(cleanUsers)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}