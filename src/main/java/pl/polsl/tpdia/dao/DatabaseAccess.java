package main.java.pl.polsl.tpdia.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.jws.soap.SOAPBinding;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Psilo on 26.08.2016.
 */
public class DatabaseAccess {
    final static String driver = "com.mysql.jdbc.Driver";
    final static String url = "jdbc:mysql://localhost:3306/";
    final static String username = "tpdia";
    final static String password = "tpdia";
    final static String databaseName = "tpdiadb";

    private String DropSQL = "DROP DATABASE IF EXISTS %1$s";
    private String CreateSQL = "CREATE DATABASE %1$s";
    private String UseSQL = "USE %1$s";

    private Table<Accounts> accounts;
    private Table<Transactions> transactions;
    private Table<AccountHolders> accountHolders;

    public DatabaseAccess() {
        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass(driver);
            cpds.setJdbcUrl(url);
            cpds.setUser(username);
            cpds.setPassword(password);

            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
            System.out.println("Loaded database driver.");

            this.accounts = new Accounts();
            this.transactions = new Transactions();
            this.accountHolders = new AccountHolders();
        } catch (PropertyVetoException e) {
            System.out.println("Failed to load database driver.");
            e.printStackTrace();
        }
    }

    public boolean initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);

            this.drobDatabaseIfExists(statement);
            this.createDatabase(statement);
            this.useDatabase(statement);
            connection.commit();

            accounts.create(statement);
            transactions.create(statement);
            accountHolders.create(statement);
            connection.commit();

            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot connect the database.");
            e.printStackTrace();
            return false;
        }
    }

    private void drobDatabaseIfExists(Statement statement) throws SQLException {
        String batchSql = String.format(DropSQL, databaseName);
        statement.addBatch(batchSql);
    }

    private void createDatabase(Statement statement) throws SQLException {
        String batchSql = String.format(CreateSQL, databaseName);
        statement.addBatch(batchSql);
    }

    private void useDatabase(Statement statement) throws SQLException {
        String batchSql = String.format(UseSQL, databaseName);
        statement.executeUpdate(batchSql);
    }
}
