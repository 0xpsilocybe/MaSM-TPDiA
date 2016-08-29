package main.java.pl.polsl.tpdia.dao;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Psilo on 29.08.2016.
 */
public class Transactions implements Table<Transactions> {
    private final String TableName = "Transactions";

    private final String CreateSQL =
        "CREATE TABLE %1$s (" +
            "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            "accountFrom INT(6) NOT NULL, " +
            "accountTo INT(6) NOT NULL, " +
            "amount NUMERIC(15,2) NOT NULL, " +
            "postingDate TIMESTAMP, " +
            "type VARCHAR(10)" +
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
        return false;
    }

    @Override
    public Iterable<Transactions> select(String where) {

        return null;
    }

    @Override
    public boolean insert(Transactions item) {

        return false;
    }

    @Override
    public boolean insert(Statement statement, Transactions item) {

        return false;
    }

    @Override
    public boolean update(Transactions item) {
        return false;
    }

    @Override
    public boolean update(Statement statement, Transactions item) {
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
