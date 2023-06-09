package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

import org.hibernate.Session;

import javax.persistence.Query;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Query query = session.createSQLQuery(createUsersTable).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        String dropTable = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(dropTable).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User(id);
        id = user.getId();
        User user1 = session.get(User.class, id);
        session.remove(user1);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete User ");
        query.executeUpdate();
        session.getTransaction().commit();
    }
}

