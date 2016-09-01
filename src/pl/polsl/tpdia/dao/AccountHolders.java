package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.AccountHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of AccountHoldersDAO
 * @author  Psilo
 */
class AccountHolders implements AccountHoldersDAO {
    private final String TableName = "AccountHolders";

    @Override
    public boolean create(Connection connection) throws SQLException {
        String createSQL = String.format(
            "CREATE TABLE `%1$s` (\n" +
                "`Id` INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                "`FirstName` VARCHAR(30) NOT NULL,\n" +
                "`LastName` VARCHAR(40) NOT NULL,\n" +
                "`Email` VARCHAR(50),\n" +
                "`BirthDate` TIMESTAMP,\n" +
                "`RegistrationDate` TIMESTAMP\n" +
            ")",
            TableName);
        Statement statement = connection.createStatement();
        return statement.execute(createSQL);
    }

    @Override
    public AccountHolder selectById(Connection connection, int id) throws SQLException {
        String selectSQL = String.format(
            "SELECT Id, FirstName, LastName, Email, BirthDate, RegistrationDate\n" +
            "FROM %1$s\n" +
            "WHERE Id = ?",
            TableName);
        PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, selectSQL, (ps) -> ps.setInt(1, id));
        ResultSet result = preparedStatement.executeQuery(selectSQL);
        if(result.next()) {
            return read(result);
        }
        return null;
    }

    @Override
    public List<AccountHolder> selectAll(Connection connection) throws SQLException {
        String selectSQL = String.format(
            "SELECT Id, FirstName, LastName, Email, BirthDate, RegistrationDate\n" +
            "FROM %1$s",
            TableName);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(selectSQL);
        List<AccountHolder> accountHolders = new ArrayList<>();
        while(result.next()) {
            AccountHolder next = read(result);
            accountHolders.add(next);
        }
        return accountHolders;
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
        String insertSQL = String.format(
                "INSERT INTO %1$s (FirstName, LastName, Email, BirthDate, RegistrationDate)\n" +
                "VALUES (?, ?, ?, ?, ?);",
                TableName);
        PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, insertSQL, (ps) -> write(ps, item));
        return preparedStatement.executeUpdate();
    }

    @Override
    public boolean update(Connection connection, AccountHolder item) throws SQLException {
        String updateSQL = String.format(
            "UPDATE %1$s\n" +
            "SET FirstName = ?, LastName = ?, Email = ?, BirthDate = ?, RegistrationDate = ?\n" +
            "WHERE Id = ?",
            TableName);
        PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, updateSQL, (ps) -> {
            write(ps, item);
            ps.setInt(6, item.getId());
        });
        return preparedStatement.execute();
    }

    private void write (PreparedStatement statement, AccountHolder item) throws SQLException {
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getEmail());
        statement.setDate(4, item.getBirthDate());
        statement.setDate(5, item.getRegistrationDate());
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        String deleteSQL = String.format(
            "DELETE FROM %1$s\n" +
            "WHERE Id = ?",
            TableName);
        PreparedStatement preparedStatement = MySQLDatabase.prepareStatement(connection, deleteSQL, (ps) -> ps.setInt(1, id));
        return preparedStatement.execute();
    }
}
