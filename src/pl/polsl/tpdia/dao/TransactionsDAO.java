package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Transactions table specific database api definitions
 * @author Psilo
 */
public interface TransactionsDAO extends Table<Transaction> {
    List<Transaction> selectByAccountHolder(Connection connection, int accountHolderId) throws SQLException;
    List<Transaction> selectAccountHolderIncoming(Connection connection, int accountHolderId) throws SQLException;
    List<Transaction> selectAccountHolderOutcoming(Connection connection, int accountHolderId) throws SQLException;
    List<Transaction> selectByDestinationAccount(Connection connection, int accountToId) throws SQLException;
    List<Transaction> selectBySourceAccount(Connection connection, int accountFromId) throws SQLException;
}
