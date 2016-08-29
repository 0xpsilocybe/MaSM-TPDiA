package main.java.pl.polsl.tpdia.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Psilo on 29.08.2016.
 */
public class AccountHolders implements Table<AccountHolders> {
    private final String TableName = "AccountHolders";

    private final String CreateSQL =
        "CREATE TABLE `%1$s` (" +
            "`id` INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            "`firstName` VARCHAR(30) NOT NULL, " +
            "`lastName` VARCHAR(40) NOT NULL, " +
            "`email` VARCHAR(50), " +
            "`birthDate` TIMESTAMP, " +
            "`registrationDate` TIMESTAMP" +
        ")";

    private final String SelectSQL =
        "SELECT id, firstName, lastName, email, birthDate, regdate " +
        "FROM %1$s " +
        "WHERE %2$s";

    private final String InsertSQL =
        "INSERT INTO %1$s (firstName, lastName, email, birthDate, registrationDate)" +
        "VALUES (%2$s, %3$s, %4$s, %5$s, %6$s)";

    private final String UpdateSQL =
        "UPDATE %1$s " +
        "SET firstName = %2$s, lastName = %3$s, email = %4$s, birthDate = %5$s, registrationDate = %6$s" +
        "WHERE id = %7$s";

    private final String DeleteSQL =
        "DELETE FROM %1$s " +
        "WHERE id = %2$s";

    @Override
    public boolean create(Statement statement) throws SQLException {
        String batchSql = String.format(CreateSQL, TableName);
        statement.addBatch(batchSql);
        return false;
    }

    @Override
    public Iterable<AccountHolders> select(Connection connection, String where) {
        String batchSql = String.format(SelectSQL, TableName, where);
        return null;
    }

    @Override
    public boolean insert(Connection connection, AccountHolders item) {
        return false;
    }

    @Override
    public boolean insert(Statement statement, AccountHolders item) {
        String batchSql = String.format(InsertSQL, TableName);
        return false;
    }

    @Override
    public boolean update(Connection connection, AccountHolders item) {
        return false;
    }

    @Override
    public boolean update(Statement statement, AccountHolders item) {

        return false;
    }

    @Override
    public boolean delete(Connection connection, int id) {
        return false;
    }

    @Override
    public boolean delete(Statement statement, int id) {
        String batchSql = String.format(InsertSQL, TableName, id);
        return false;
    }
}
