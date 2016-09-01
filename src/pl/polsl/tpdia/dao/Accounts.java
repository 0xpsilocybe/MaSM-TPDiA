package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * MySQL implementation of AccountsDAO
 * @author  Psilo
 */
class Accounts implements AccountsDAO {
    private final String TableName = "Accounts";

    @Override
    public boolean create(Connection connection) throws SQLException {
        String createSQL = String.format(
            "CREATE TABLE %1$s (\n" +
                "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                "accountHolder INT(6) NOT NULL,\n" +
                "balance NUMERIC(15,2) NOT NULL,\n" +
                "currency ENUM('PLN', 'EUR', 'USD', 'GBP', 'CHF'),\n" +
                "type VARCHAR(25)\n" +
            ")",
            TableName);
        Statement statement = connection.createStatement();
        return statement.execute(createSQL);
    }

    @Override
    public Account selectById(Connection connection, int id) {
        return null;
    }

    @Override
    public List<Account> selectAll(Connection connection) {
        return null;
    }

    @Override
    public int insert(Connection connection, Account item) {
        return -1;
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
