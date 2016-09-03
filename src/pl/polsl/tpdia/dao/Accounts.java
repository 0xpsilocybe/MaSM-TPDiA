package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Account;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of AccountsDAO
 *
 * @author Psilo
 */
class Accounts implements AccountsDAO {
    private final String TableName = "Accounts";

    @Override
    public boolean create(Connection connection) throws SQLException {
        String createSQL = String.format(
                "CREATE TABLE %1$s (\n" +
                        "Id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                        "AccountHolder INT(6) UNSIGNED NOT NULL,\n" +
                        "Balance NUMERIC(15,2) NOT NULL,\n" +
                        "Currency ENUM('PLN', 'EUR', 'USD', 'GBP', 'CHF'),\n" +
                        "Type VARCHAR(25)\n" +
                        ")",
                TableName);
        try (Statement statement = connection.createStatement()) {
            return statement.execute(createSQL);
        }
    }

    public boolean createForeignKey(Connection connection) throws SQLException {
        String accountHolderforeignKeySQL = String.format(
                "ALTER TABLE `%1$s`\n" +
                        "ADD CONSTRAINT fk_AccHold_Id\n" +
                        "FOREIGN KEY (AccountHolder)\n" +
                        "REFERENCES AccountHolders(Id)\n",
                TableName);
        try (Statement statement = connection.createStatement()) {
            return statement.execute(accountHolderforeignKeySQL);
        }
    }

    @Override
    public Account selectById(Connection connection, int id) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountHolder, Balance, Currency, Type\n" +
                        "FROM %1$s\n" +
                        "WHERE Id = ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setInt(1, id))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    return read(result);
                }
                return null;
            }
        }
    }

    @Override
    public List<Account> selectAll(Connection connection) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountHolder, Balance, Currency, Type\n" +
                        "FROM %1$s",
                TableName);
        try (Statement statement = connection.createStatement()) {
            try (ResultSet result = statement.executeQuery(selectSQL)) {
                List<Account> accounts = new ArrayList<>();
                while (result.next()) {
                    Account next = read(result);
                    accounts.add(next);
                }
                return accounts;
            }
        }
    }

    @Override
    public List<Account> selectByAccountHolder(Connection connection, int accountHolderId) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountHolder, Balance, Currency, Type\n" +
                        "FROM %1$s\n" +
                        "WHERE AccountHolder = ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setInt(1, accountHolderId))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Account> accounts = new ArrayList<>();
                while (result.next()) {
                    Account next = read(result);
                    accounts.add(next);
                }
                return accounts;
            }
        }
    }

    @Override
    public List<Account> selectWithMinimalBalance(Connection connection, BigDecimal minimumBalance) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountHolder, Balance, Currency, Type\n" +
                        "FROM %1$s\n" +
                        "WHERE Balance >= ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setBigDecimal(1, minimumBalance))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Account> accounts = new ArrayList<>();
                while (result.next()) {
                    Account next = read(result);
                    accounts.add(next);
                }
                return accounts;
            }
        }
    }

    @Override
    public List<Account> selectWithMaximalBalance(Connection connection, BigDecimal maximumBalance) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountHolder, Balance, Currency, Type\n" +
                        "FROM %1$s\n" +
                        "WHERE Balance <= ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setBigDecimal(1, maximumBalance))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Account> accounts = new ArrayList<>();
                while (result.next()) {
                    Account next = read(result);
                    accounts.add(next);
                }
                return accounts;
            }
        }
    }

    private Account read(ResultSet result) throws SQLException {
        Account account = new Account();
        account.setId(result.getInt("Id"));
        account.setAccountHolderId(result.getInt("AccountHolder"));
        account.setBalance(result.getBigDecimal("Balance"));
        account.setCurrency(result.getString("Currency"));
        account.setType(result.getString("Type"));
        return account;
    }

    @Override
    public int insert(Connection connection, Account item) throws SQLException {
        String insertSQL = String.format(
                "INSERT INTO %1$s (AccountHolder, Balance, Currency, Type)\n" +
                        "VALUES (?, ?, ?, ?);",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, insertSQL, (ps) -> write(ps, item))) {
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean update(Connection connection, Account item) throws SQLException {
        String updateSQL = String.format(
                "UPDATE %1$s\n" +
                        "SET AccountHolder = ?, Balance = ?, Currency = ?, Type = ?\n" +
                        "WHERE Id = ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, updateSQL, (ps) -> {
            write(ps, item);
            ps.setInt(5, item.getId());
        })) {
            return preparedStatement.execute();
        }
    }

    private void write(PreparedStatement statement, Account item) throws SQLException {
        statement.setInt(1, item.getAccountHolderId());
        statement.setBigDecimal(2, item.getBalance());
        statement.setString(3, item.getCurrency());
        statement.setString(4, item.getType());
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        String deleteSQL = String.format(
                "DELETE FROM %1$s\n" +
                        "WHERE Id = ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, deleteSQL, (ps) -> ps.setInt(1, id))) {
            return preparedStatement.execute();
        }
    }
}
