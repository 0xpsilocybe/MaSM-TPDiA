package main.java.pl.polsl.tpdia.dao;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Psilo on 29.08.2016.
 */
public class Accounts implements Table<Accounts> {
    private final String TableName = "Accounts";

    private final String CreateSQL =
        "CREATE TABLE %1$s (" +
            "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            "accountHolder INT(6) NOT NULL, " +
            "balance NUMERIC(15,2) NOT NULL, " +
            "currency ENUM('PLN', 'EUR', 'USD', 'GBP', 'CHF'), " +
            "type VARCHAR(25)" +
        ")";

    private final String SelectSQL =
        "";

    private final String InsertSQL =
        "";

    private final String UpdateSQL =
        "";

    private final String DeleteSQL =
        "DELETE FROM %1$s " +
        "WHERE id = %2$s";

    @Override
    public boolean create(Statement statement) throws SQLException {
        String batchSql = String.format(CreateSQL, TableName);
        statement.addBatch(batchSql);
        return true;
    }

    @Override
    public Iterable<Accounts> select(String where) {
        return null;
    }

    @Override
    public boolean insert(Accounts item) {
        return false;
    }

    @Override
    public boolean insert(Statement statement, Accounts item) {
        return false;
    }

    @Override
    public boolean update(Accounts item) {
        return false;
    }

    @Override
    public boolean update(Statement statement, Accounts item) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Statement statement, int id) {
        return false;
    }
}
