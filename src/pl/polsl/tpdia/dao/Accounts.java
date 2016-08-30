package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * Created by Psilo on 29.08.2016.
 */
public class Accounts implements AccountsDAO {
    private final String TableName = "Accounts";

    @Override
    public boolean create(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createSQL = String.format(
            "CREATE TABLE %1$s (" +
                "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "accountHolder INT(6) NOT NULL, " +
                "balance NUMERIC(15,2) NOT NULL, " +
                "currency ENUM('PLN', 'EUR', 'USD', 'GBP', 'CHF'), " +
                "type VARCHAR(25)" +
            ")",
            TableName);
        return statement.execute(createSQL);
    }

    @Override
    public Account selectById(Connection connection, int id) {
        return null;
    }

    @Override
    public Collection<Account> selectAll(Connection connection) {
        return null;
    }

    @Override
    public boolean insert(Connection connection, Account item) {
        return false;
    }

    @Override
    public boolean update(Connection connection, Account item) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, int id) {
        return false;
    }
}
