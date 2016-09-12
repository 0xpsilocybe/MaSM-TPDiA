package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.AccountHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of AccountHoldersDAO
 */
class AccountHolders implements AccountHoldersDAO {
    @Override
    public boolean create(Connection connection) throws SQLException {
        String createSQL =
            "CREATE TABLE AccountHolders (\n" +
                "Id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                "FirstName VARCHAR(30) NOT NULL,\n" +
                "LastName VARCHAR(40) NOT NULL,\n" +
                "Email VARCHAR(100),\n" +
                "BirthDate DATE,\n" +
                "RegistrationDate DATE\n" +
            ")";
        try (Statement statement = connection.createStatement()) {
            return statement.execute(createSQL);
        }
    }

    @Override
    public AccountHolder selectById(Connection connection, int id) throws SQLException {
        String selectSQL =
            "SELECT Id, FirstName, LastName, Email, BirthDate, RegistrationDate\n" +
            "FROM AccountHolders\n" +
            "WHERE Id = ?";
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
    public List<AccountHolder> selectAll(Connection connection) throws SQLException {
        String selectSQL =
            "SELECT Id, FirstName, LastName, Email, BirthDate, RegistrationDate\n" +
            "FROM AccountHolders";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet result = statement.executeQuery(selectSQL)) {
                List<AccountHolder> accountHolders = new ArrayList<>();
                while (result.next()) {
                    AccountHolder next = read(result);
                    accountHolders.add(next);
                }
                return accountHolders;
            }
        }
    }

    private AccountHolder read(ResultSet result) throws SQLException {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(result.getInt("Id"));
        accountHolder.setFirstName(result.getString("FirstName"));
        accountHolder.setLastName(result.getString("LastName"));
        accountHolder.setEmail(result.getString("Email"));
        accountHolder.setBirthDate(result.getDate("BirthDate"));
        accountHolder.setRegistrationDate(result.getDate("RegistrationDate"));
        return accountHolder;
    }

    @Override
    public int insert(Connection connection, AccountHolder item) throws SQLException {
        String insertSQL =
            "INSERT INTO AccountHolders (FirstName, LastName, Email, BirthDate, RegistrationDate)\n" +
            "VALUES (?, ?, ?, ?, ?);";
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
    public boolean update(Connection connection, AccountHolder item) throws SQLException {
        String updateSQL =
            "UPDATE AccountHolders\n" +
            "SET FirstName = ?, LastName = ?, Email = ?, BirthDate = ?, RegistrationDate = ?\n" +
            "WHERE Id = ?";
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, updateSQL, (ps) -> {
            write(ps, item);
            ps.setInt(6, item.getId());
        })) {
            return preparedStatement.execute();
        }
    }

    private void write(PreparedStatement statement, AccountHolder item) throws SQLException {
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getEmail());
        statement.setDate(4, item.getBirthDate());
        statement.setDate(5, item.getRegistrationDate());
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        String deleteSQL =
            "DELETE FROM AccountHolders\n" +
            "WHERE Id = ?";
        try (PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, deleteSQL, (ps) -> ps.setInt(1, id))) {
            return preparedStatement.execute();
        }
    }
}
