package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of TransactionsDAO
 */
class Transactions implements TransactionsDAO {
    private final String TableName = "Transactions";

    @Override
    public boolean create(Connection connection) throws SQLException {
        String createSQL = String.format(
                "CREATE TABLE %1$s (\n" +
                        "Id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                        "AccountFrom INT(6) UNSIGNED NOT NULL,\n" +
                        "AccountTo INT(6) UNSIGNED NOT NULL,\n" +
                        "Amount NUMERIC(15,2) NOT NULL,\n" +
                        "PostingDate TIMESTAMP,\n" +
                        "Type VARCHAR(10)\n" +
                        ")",
                TableName);
        try (Statement statement = connection.createStatement()) {
            return statement.execute(createSQL);
        }
    }

    public boolean createForeignKey(Connection connection) throws SQLException {
        String accountFromForeignKeySQL = String.format(
                "ALTER TABLE `%1$s`\n" +
                        "ADD CONSTRAINT fk_AccFrom_Id\n" +
                        "FOREIGN KEY (AccountFrom)\n" +
                        "REFERENCES Accounts(Id)\n",
                TableName);
        String accountToForeignKeySQL = String.format(
                "ALTER TABLE `%1$s`\n" +
                        "ADD CONSTRAINT fk_AccTo_Id\n" +
                        "FOREIGN KEY (AccountTo)\n" +
                        "REFERENCES Accounts(Id)\n",
                TableName);
        try (Statement statement = connection.createStatement()) {
            return (
                    statement.execute(accountFromForeignKeySQL) &&
                    statement.execute(accountToForeignKeySQL)
            );
        }
    }

    @Override
    public Transaction selectById(Connection connection, int id) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountFrom, AccountTo, Amount, PostingDate, Type\n" +
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
    public List<Transaction> selectAll(Connection connection) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountFrom, AccountTo, Amount, PostingDate, Type\n" +
                        "FROM %1$s",
                TableName);
        try (Statement statement = connection.createStatement()) {
            try (ResultSet result = statement.executeQuery(selectSQL)) {
                List<Transaction> transactions = new ArrayList<>();
                while (result.next()) {
                    Transaction next = read(result);
                    transactions.add(next);
                }
                return transactions;
            }
        }
    }

    @Override
    public List<Transaction> selectByAccountHolder(Connection connection, int accountHolderId) throws SQLException {
        String selectSQL = String.format(
                "SELECT t.Id, t.AccountFrom, t.AccountTo, t.Amount, t.PostingDate, t.Type\n" +
                        "FROM %1$s t, %2$s a\n" +
                        "WHERE (t.AccountFrom = a.Id AND a.AccountHolder = ?)\n" +
                        "OR (t.AccountTo = a.Id AND a.AccountHolder = ?)",
                TableName, "Accounts");
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> {
            ps.setInt(1, accountHolderId);
            ps.setInt(2, accountHolderId);
        })) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (result.next()) {
                    Transaction next = read(result);
                    transactions.add(next);
                }
                return transactions;
            }
        }
    }

    @Override
    public List<Transaction> selectAccountHolderIncoming(Connection connection, int accountHolderId) throws SQLException {
        String selectSQL = String.format(
                "SELECT t.Id, t.AccountFrom, t.AccountTo, t.Amount, t.PostingDate, t.Type\n" +
                        "FROM %1$s t, %2$s a\n" +
                        "WHERE t.AccountTo = a.Id\n" +
                        "AND a.AccountHolder = ?",
                TableName, "Accounts");
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setInt(1, accountHolderId))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (result.next()) {
                    Transaction next = read(result);
                    transactions.add(next);
                }
                return transactions;
            }
        }
    }

    @Override
    public List<Transaction> selectAccountHolderOutcoming(Connection connection, int accountHolderId) throws SQLException {
        String selectSQL = String.format(
                "SELECT t.Id, t.AccountFrom, t.AccountTo, t.Amount, t.PostingDate, t.Type\n" +
                        "FROM %1$s t, %2$s a\n" +
                        "WHERE t.AccountFrom = a.Id\n" +
                        "AND a.AccountHolder = ?",
                TableName, "Accounts");
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setInt(1, accountHolderId))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (result.next()) {
                    Transaction next = read(result);
                    transactions.add(next);
                }
                return transactions;
            }
        }
    }

    @Override
    public List<Transaction> selectByDestinationAccount(Connection connection, int accountToId) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountFrom, AccountTo, Amount, PostingDate, Type\n" +
                        "FROM %1$s\n" +
                        "WHERE AccountTo = ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setInt(1, accountToId))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (result.next()) {
                    Transaction next = read(result);
                    transactions.add(next);
                }
                return transactions;
            }
        }
    }

    @Override
    public List<Transaction> selectBySourceAccount(Connection connection, int accountFromId) throws SQLException {
        String selectSQL = String.format(
                "SELECT Id, AccountFrom, AccountTo, Amount, PostingDate, Type\n" +
                        "FROM %1$s\n" +
                        "WHERE AccountFrom = ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setInt(1, accountFromId))) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (result.next()) {
                    Transaction next = read(result);
                    transactions.add(next);
                }
                return transactions;
            }
        }
    }

    private Transaction read(ResultSet result) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(result.getInt("Id"));
        transaction.setAccountFromId(result.getInt("AccountFrom"));
        transaction.setAccountToId(result.getInt("AccountTo"));
        transaction.setAmount(result.getBigDecimal("Amount"));
        transaction.setPostingDate(result.getDate("PostingDate"));
        transaction.setType(result.getString("Type"));
        return transaction;
    }

    @Override
    public int insert(Connection connection, Transaction item) throws SQLException {
        String insertSQL = String.format(
                "INSERT INTO %1$s (AccountFrom, AccountTo, Amount, PostingDate, Type)\n" +
                        "VALUES (?, ?, ?, ?, ?);",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(
                connection, insertSQL, Statement.RETURN_GENERATED_KEYS, (ps) -> write(ps, item))) {
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            return -1;
        }
    }

    @Override
    public boolean update(Connection connection, Transaction item) throws SQLException {
        String updateSQL = String.format(
                "UPDATE %1$s\n" +
                        "SET AccountFrom = ?, AccountTo = ?, Amount = ?, PostingDate = ?, Type = ?\n" +
                        "WHERE Id = ?",
                TableName);
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, updateSQL, (ps) -> {
            write(ps, item);
            ps.setInt(6, item.getId());
        })) {
            return preparedStatement.execute();
        }
    }

    private void write(PreparedStatement statement, Transaction item) throws SQLException {
        statement.setInt(1, item.getAccountFromId());
        statement.setInt(2, item.getAccountToId());
        statement.setBigDecimal(3, item.getAmount());
        statement.setDate(4, item.getPostingDate());
        statement.setString(5, item.getType());
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
