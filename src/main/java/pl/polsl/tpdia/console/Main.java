package pl.polsl.tpdia.console;

import pl.polsl.tpdia.dao.AccountsDAO;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.helpers.AccountHolderGenerator;
import pl.polsl.tpdia.helpers.Generator;
import pl.polsl.tpdia.models.AccountHolder;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.dao.AccountHoldersDAO;
import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.models.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            logger.trace("Application start");
            MySQLDatabase database = new MySQLDatabase();
            logger.trace("Database connected and restored.");

            Connection connection = MySQLDatabase.getConnection();
            connection.setAutoCommit(false);

            logger.trace("Test AccountHoldersDAO methods");
            AccountHoldersDAO accountHolders = database.getAccountHolders();
            Generator<AccountHolder> ahGen = new AccountHolderGenerator();
            for(int i = 0; i< 100; i++) {
                AccountHolder a = ahGen.generate();
                accountHolders.insert(connection, a);
            }
            connection.commit();
            connection.close();
            logger.trace("Application close");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
