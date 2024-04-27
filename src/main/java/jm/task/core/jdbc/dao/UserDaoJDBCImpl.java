package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "CREATE TABLE `Users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQl = "DROP TABLES IF EXISTS `Users`;";
            statement.executeUpdate(SQl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("Insert into Users(name,lastName,age) values (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем —" + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE from Users where id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String SQL = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            users.forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "TRUNCATE Users";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
