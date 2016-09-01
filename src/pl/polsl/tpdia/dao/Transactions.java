package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * MySQL implementation of TransactionsDAO
 * @author  Psilo
 */
class Transactions implements TransactionsDAO {
    private final String TableName = "Transactions";

    @Override
    public boolean create(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createSQL = String.format(
            "CREATE TABLE %1$s (\n" +
                "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                "accountFrom INT(6) NOT NULL,\n" +
                "accountTo INT(6) NOT NULL,\n" +
                "amount NUMERIC(15,2) NOT NULL,\n" +
                "postingDate TIMESTAMP,\n" +
                "type VARCHAR(10)\n" +
            ")",
            TableName);
        return statement.execute(createSQL);
    }

    @Override
    public Transaction selectById(Connection connection, int id) {
        return null;
    }

    @Override
    public List<Transaction> selectAll(Connection connection) {
        return null;
    }

    @Override
    public int insert(Connection connection, Transaction item) {
        return -1;
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
