package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl extends Util implements UserDao {
    private  SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        String sql = "CREATE TABLE User" +
                "(Id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "Name VARCHAR(45)," +
                "lastName VARCHAR(45)," +
                "age TINYINT(3) )";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка создания");
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        String sql = "DROP TABLE User";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка дропа");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        String sql = "INSERT INTO User (Name, lastName, age) VALUES (?, ?, ?)";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery(sql);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {

        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        String sql = "DELETE FROM User WHERE ID = ?";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;

        try (Session session = sessionFactory.openSession()) {
            userList = session.createQuery("FROM User").list();
            return userList;
        }
    }

        @Override
        public void cleanUsersTable () {
            Transaction transaction = null;

            String sql = "TRUNCATE TABLE User";

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {

            }
        }
    }
