package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS USERSTABLE" +
                "(id INTEGER NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(100) not null, " +
                "lastname VARCHAR(100) not null, " +
                "age INTEGER (128) not null, " +
                "PRIMARY KEY(id))";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drob = "DROP TABLE IF EXISTS USERSTABLE";
        try (Statement statement = Util.getConnection().createStatement();) {
            statement.execute(drob);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        name = user.getName();
        lastName = user.getLastName();
        age = user.getAge();
        String save = "INSERT INTO USERSTABLE (name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(save)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String remove = "DELETE FROM USERSTABLE WHERE id=?";
        User user = new User(id);
        id = user.getId();

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(remove)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String get = "SELECT id, name, lastname, age from USERSTABLE";
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(get);
            while(resultSet.next()){
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
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        String clean = "TRUNCATE USERSTABLE";
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(clean)){
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}