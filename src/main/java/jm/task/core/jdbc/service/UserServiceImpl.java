package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends Util implements UserService {

    Connection connection = getConnection();
    public void createUsersTable() {

        String sql = "CREATE TABLE User" +
                "(Id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "Name VARCHAR(45)," +
                "lastName VARCHAR(45)," +
                "age TINYINT(3) )";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            System.out.println("Таблица с таким именем уже существует");
        }
    }

    public void dropUsersTable() {

        String sql = "DROP TABLE User";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            System.out.println("Таблицы с таким именем не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        String sql = "INSERT INTO User (Name, lastName, age) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Ошибка добавления в таблицу");
        }


    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT Id, Name, secondName, age FROM USER WHERE Id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Ошибка удаления юзера по id");
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT Id, Name, lastName, age FROM USER";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Ошибка в списке всех юзеров");
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        Statement statement = null;

        String sql = "DELETE FROM User";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            System.out.println("Ошибка очистки таблицы");
        }
    }
}
