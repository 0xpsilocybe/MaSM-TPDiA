package pl.polsl.tpdia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by Psilo on 26.08.2016.
 */
public class DatabaseAccess {
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    final static String url = "jdbc:mysql://localhost:3306/";
    final static String username = "tpdia";
    final static String password = "tpdia";
    final static String databaseName = "tpdiadb";
    final static String testTableName = "testTable";

    DatabaseAccess() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Loaded database driver.");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load database driver.");
            e.printStackTrace();
        }
    }

    public boolean initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
            statement.executeUpdate("CREATE DATABASE " + databaseName);
            statement.executeUpdate("USE " + databaseName);

            statement.executeUpdate("CREATE TABLE " + testTableName + "(" +
                    "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                    "firstname VARCHAR(30) NOT NULL," +
                    "lastname VARCHAR(30) NOT NULL," +
                    "email VARCHAR(50)," +
                    "reg_date TIMESTAMP)");

            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot connect the database.");
            e.printStackTrace();
            return false;
        }
    }
}
