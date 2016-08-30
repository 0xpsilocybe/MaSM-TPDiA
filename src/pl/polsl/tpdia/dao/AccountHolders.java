package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.AccountHolder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * Created by Psilo on 29.08.2016.
 */
public class AccountHolders implements AccountHoldersDAO {
    private final String TableName = "AccountHolders";

    @Override
    public boolean create(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createSQL = String.format(
            "CREATE TABLE `%1$s` (" +
                "`id` INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "`firstName` VARCHAR(30) NOT NULL, " +
                "`lastName` VARCHAR(40) NOT NULL, " +
                "`email` VARCHAR(50), " +
                "`birthDate` TIMESTAMP, " +
                "`registrationDate` TIMESTAMP" +
            ")",
            TableName);
        return statement.execute(createSQL);
    }

    @Override
    public AccountHolder selectById(Connection connection, int id) {
        String selectSQL = String.format(
            "SELECT id, firstName, lastName, email, birthDate, registrationDate " +
            "FROM %1$s " +
            "WHERE %2$s",
            TableName, id);

        return null;
    }

    @Override
    public Collection<AccountHolder> selectAll(Connection connection) {
        String selectSQL = String.format(
            "SELECT id, firstName, lastName, email, birthDate, registrationDate " +
            "FROM %1$s",
            TableName);

        return null;
    }

    @Override
    public boolean insert(Connection connection, AccountHolder item) {
        String insertSQL = String.format(
                "INSERT INTO %1$s (firstName, lastName, email, birthDate, registrationDate)" +
                "VALUES (%2$s, %3$s, %4$s, %5$s, %6$s)",
                TableName, item.getFirstName(), item.getLastName(), item.getEmail(), item.getBirthDate(), item.getRegistrationDate());

        return false;
    }

    @Override
    public boolean update(Connection connection, AccountHolder item) {
        String updateSQL = String.format(
            "UPDATE %1$s " +
            "SET firstName = %2$s, lastName = %3$s, email = %4$s, birthDate = %5$s, registrationDate = %6$s" +
            "WHERE id = %7$s",
            TableName, item.getFirstName(), item.getLastName(), item.getEmail(), item.getBirthDate(), item.getRegistrationDate(), item.getId());

        return false;
    }

    @Override
    public boolean delete(Connection connection, int id) {
        String deleteSQL = String.format(
            "DELETE FROM %1$s " +
            "WHERE id = %2$s",
            TableName, id);

        return false;
    }
}
