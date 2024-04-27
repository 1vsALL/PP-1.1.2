package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String USER = "root";
    private static String BASE = "jdbc:mysql://localhost:3306/kataAcademy";
    private static String PASSWORD = "rootroot";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(BASE, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
