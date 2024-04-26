package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util=new Util();
    private Connection connection;

    public UserDaoJDBCImpl(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
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
        try (Statement statement = util.getConnection().createStatement()) {
            String SQl = "DROP TABLES IF EXISTS `Users`;";
            statement.executeUpdate(SQl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement("Insert into Users(name,lastName,age) values (?,?,?)")) {
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
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement("DELETE from Users where id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Statement statement = util.getConnection().createStatement()) {
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
        try (Statement statement = util.getConnection().createStatement()) {
            String SQL = "TRUNCATE Users";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
