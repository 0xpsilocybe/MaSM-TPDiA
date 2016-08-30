package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * Created by Psilo on 29.08.2016.
 */
public class Transactions implements TransactionsDAO {
    private final String TableName = "Transactions";

    @Override
    public boolean create(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createSQL = String.format(
            "CREATE TABLE %1$s (" +
                "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "accountFrom INT(6) NOT NULL, " +
                "accountTo INT(6) NOT NULL, " +
                "amount NUMERIC(15,2) NOT NULL, " +
                "postingDate TIMESTAMP, " +
                "type VARCHAR(10)" +
            ")",
            TableName);
        return statement.execute(createSQL);
    }

    @Override
    public Transaction selectById(Connection connection, int id) {
        return null;
    }

    @Override
    public Collection<Transaction> selectAll(Connection connection) {
        return null;
    }

    @Override
    public boolean insert(Connection connection, Transaction item) {
        return false;
    }

    @Override
    public boolean update(Connection connection, Transaction item) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, int id) {
        return false;
    }
}
