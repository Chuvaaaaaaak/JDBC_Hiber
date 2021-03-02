package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

        userDaoHibernate.dropUsersTable();
        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("test", "fgf", (byte) 12);
        userDaoHibernate.saveUser("test", "fgf", (byte) 12);


    }
}
