package pl.polsl.tpdia.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.*;

/**
 * MySQL database manager
 */
public class MySQLDatabase {
    private static DataSource dataSource;
    private final static String driver = "com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://localhost:3306/";
    private final static String username = "tpdia";
    private final static String password = "tpdia";
    private final static String databaseName = "tpdiadb";

    static {
        boolean databaseCreated = createDatabase();
        if (databaseCreated) {
            /* Initialize pooled datasource with C3P0 */
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            try {
                cpds.setDriverClass(driver);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            cpds.setJdbcUrl(url + databaseName);
            cpds.setUser(username);
            cpds.setPassword(password);
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
            dataSource = cpds;
        }
    }

    /**
     * Creates database with {@link #databaseName}.
     * Current database will be dropped.
     *
      * @return Operation success
     */
    private static boolean createDatabase() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            dropDatabaseIfExistsBatch(statement);
            createDatabaseBatch(statement);
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get database connection from the pool
     * @return Pooled connection to MySQL database
     * @throws SQLException when connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Prepares given SQL statement
     * @param connection Connection to database
     * @param sql String statement to prepare
     * @param setter Exact implementation of statement setter for given sql
     * @return Prepared statement with set variables
     * @throws SQLException when some of the statements variables cannot be set
     */
    static PreparedStatement prepareStatement(
            Connection connection,
            String sql,
            PreparedStatementSetter setter)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        setter.setValues(statement);
        return statement;
    }

    /**
     * Prepares given SQL statement
     * @param connection Connection to database
     * @param sql String statement to prepare
     * @param statementParam Identifier generated in database
     * @param setter Exact implementation of statement setter for given sql
     * @return Prepared statement with set variables
     * @throws SQLException when some of the statements variables cannot be set
     */
    static PreparedStatement prepareStatement(
            Connection connection,
            String sql,
            int statementParam,
            PreparedStatementSetter setter)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, statementParam);
        setter.setValues(statement);
        return statement;
    }

    private static void dropDatabaseIfExistsBatch(Statement statement) throws SQLException {
        String dropSQL = String.format("DROP DATABASE IF EXISTS %1$s", databaseName);
        statement.addBatch(dropSQL);
    }

    private static void createDatabaseBatch(Statement statement) throws SQLException {
        String createSQL = String.format("CREATE DATABASE %1$s", databaseName);
        statement.addBatch(createSQL);
    }


    private AccountsDAO accounts;
    private TransactionsDAO transactions;
    private AccountHoldersDAO accountHolders;

    /**
     * Initializes MySQL DAO and database
     */
    public MySQLDatabase() {
        this.accounts = new Accounts();
        this.transactions = new Transactions();
        this.accountHolders = new AccountHolders();
        this.createTables();
    }

    /**
     * Restores basic database schema
     * @return Operation success
     */
    private boolean createTables() {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            accounts.create(connection);
            transactions.create(connection);
            accountHolders.create(connection);
            connection.commit();
            accounts.createForeignKey(connection);
            transactions.createForeignKey(connection);
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public AccountsDAO getAccounts() {
        return this.accounts;
    }

    public AccountHoldersDAO getAccountHolders() {
        return this.accountHolders;
    }

    public TransactionsDAO getTransactions() {
        return this.transactions;
    }
}
