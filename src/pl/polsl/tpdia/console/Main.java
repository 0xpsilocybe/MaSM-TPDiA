package pl.polsl.tpdia.console;

import pl.polsl.tpdia.dao.AccountHoldersDAO;
import pl.polsl.tpdia.dao.AccountsDAO;
import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.models.AccountHolder;
import pl.polsl.tpdia.models.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            MySQLDatabase database = new MySQLDatabase();
            Connection connection = MySQLDatabase.getConnection();
            connection.setAutoCommit(false);

            //Test AccountHoldersDAO methods
            AccountHoldersDAO accountHolders =  database.getAccountHolders();
            for (int i = 0; i < 10; i++) {
                AccountHolder accountHolder = new AccountHolder();
                accountHolder.setFirstName("Name_" + i);
                accountHolder.setLastName("Surname_" + i);
                accountHolder.setEmail("name.surname@domain.com");
                accountHolder.setBirthDate(new Date(31536000000L));
                accountHolder.setRegistrationDate(new Date(315360000000L));
                accountHolders.insert(connection, accountHolder);
            }
            connection.commit();
            ArrayList<AccountHolder> selectAccountHolders = (ArrayList<AccountHolder>) accountHolders.selectAll(connection);
            selectAccountHolders.get(0).setFirstName("zmieniono");
            accountHolders.update(connection, selectAccountHolders.get(0));
            accountHolders.delete(connection, 2);
            connection.commit();

            //Test AccountsDAO methods
            AccountsDAO accounts =  database.getAccounts();
            for (int i = 0; i < 300; i++) {
                Account account = new Account();
                account.setAccountHolderId(i % 10 + 1);
                account.setBalance(BigDecimal.valueOf(i * 1000 + 1000));
                account.setCurrency("PLN");
                account.setType("Konto osobiste");
                accounts.insert(connection, account);
            }
            connection.commit();
            ArrayList<Account> selectAccounts = (ArrayList<Account>) accounts.selectAll(connection);
            selectAccounts.get(0).setBalance(BigDecimal.valueOf(2000000.12));
            accounts.update(connection, selectAccounts.get(0));
            accounts.delete(connection, 2);
            connection.commit();

            //Test AccountsDAO methods
            TransactionsDAO transactions =  database.getTransactions();
            for (int i = 0; i < 3000; i++) {
                Transaction transaction = new Transaction();
                transaction.setAccountFromId(i % 300 + 1);
                transaction.setAccountToId(i % 299 + 2);
                transaction.setAmount(BigDecimal.valueOf(i + 123.56));
                transaction.setType("OWN");
                transactions.insert(connection, transaction);
            }
            connection.commit();
            ArrayList<Transaction> selectTransactions = (ArrayList<Transaction>) transactions.selectAll(connection);
            selectTransactions.get(0).setAmount(BigDecimal.valueOf(8888.88));
            transactions.update(connection, selectTransactions.get(0));
            transactions.delete(connection, 2);
            connection.commit();

            connection.close();
            System.in.read();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
