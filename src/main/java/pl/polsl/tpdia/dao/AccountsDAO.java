package pl.polsl.tpdia.dao;

import pl.polsl.tpdia.models.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Accounts table specific database api definitions
 */
public interface AccountsDAO extends Table<Account> {
    List<Account> selectByAccountHolder(Connection connection, int accountHolderId) throws SQLException;
    List<Account> selectWithMinimalBalance(Connection connection, BigDecimal minimumBalance) throws SQLException;
    List<Account> selectWithMaximalBalance(Connection connection, BigDecimal maximumBalance) throws SQLException;
}
