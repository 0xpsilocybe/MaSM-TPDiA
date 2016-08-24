package pl.polsl.tpdia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    final static String url = "jdbc:mysql://localhost:3306/";
    final static String username = "tpdia";
    final static String password = "tpdia";
    final static String databaseName = "tpdiadb";
    final static String testTableName = "testTable";

    public static void main(String[] args) {

        //initializeDatabase();

        Runnable task = () -> {
          String threadName = Thread.currentThread().getName();
            System.out.println("Hi " + threadName);
        };
        task.run();

        Thread thread = new Thread(task);
        thread.start();


        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });
    }

    private static void initializeDatabase() {
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
