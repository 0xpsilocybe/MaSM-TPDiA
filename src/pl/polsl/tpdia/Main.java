package pl.polsl.tpdia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/";
        String username = "tpdia";
        String password = "tpdia";
        String databaseName = "tpdiadb";
        String testTableName = "testTable";

        System.out.println("Connecting database...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
