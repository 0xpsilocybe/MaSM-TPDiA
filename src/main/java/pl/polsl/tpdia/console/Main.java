package pl.polsl.tpdia.console;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import pl.polsl.tpdia.dao.AccountHoldersDAO;
import pl.polsl.tpdia.dao.AccountsDAO;
import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.helpers.AccountGenerator;
import pl.polsl.tpdia.helpers.AccountHolderGenerator;
import pl.polsl.tpdia.helpers.Generator;
import pl.polsl.tpdia.helpers.TransactionGenerator;
import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.models.AccountHolder;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.generator.QueriesGenerator;
import pl.polsl.tpdia.queries.handler.MasmQueryWorker;
import pl.polsl.tpdia.queries.handler.MasmQueryWorkerImpl;
import pl.polsl.tpdia.updates.generator.TransactionUpdatesGenerator;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorkerImpl;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            logger.trace("Application start");

            logger.trace("Creating database");
            MySQLDatabase database = new MySQLDatabase();

            logger.trace("Populating database with random test data");
            List<Integer> transactionIds = populateDbWithTestData(database);

            MasmUpdateWorker<Transaction> transactionMasmUpdateWorker = new MasmUpdateWorkerImpl<>();
            TransactionUpdatesGenerator transactionUpdatesGenerator = new TransactionUpdatesGenerator(transactionMasmUpdateWorker, transactionIds);

            (new Thread(transactionMasmUpdateWorker)).start();
            (new Thread(transactionUpdatesGenerator)).start();

            MasmQueryWorker<Transaction> transactionMasmQueryWorker = new MasmQueryWorkerImpl(transactionUpdatesGenerator, transactionMasmUpdateWorker, database);
            QueriesGenerator transactionQueriesGenerator = new QueriesGenerator(transactionMasmQueryWorker);

            (new Thread(transactionMasmQueryWorker)).start();
            (new Thread(transactionQueriesGenerator)).start();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final int ACCOUNT_HOLDERS_COUNT = 1;
    private static final int MIN_ACCOUNTS_PER_HOLDER_COUNT = 1;
    private static final int MAX_ACCOUNTS_PER_HOLDER_COUNT = 3;
    private static final int MIN_TRANSACTIONS_PER_ACCOUNT_COUNT = 5;
    private static final int MAX_TRANSACTIONS_PER_ACCOUNT_COUNT = 10;

    private static List<Integer> populateDbWithTestData(MySQLDatabase database) throws SQLException {
        SecureRandom random = new SecureRandom();
        logger.trace("Creating random account holders");
        List<Integer> accountHoldersIds = createRandomAccountHolders(database, random);

        logger.trace("Creating random accounts");
        List<Integer> accountsIds = createRandomAccountsForAccountHolders(database, random, accountHoldersIds);

        logger.trace("Creating random transactions");
        return createRandomTransactionsForAccounts(database, random, accountsIds);
    }

    private static List<Integer> createRandomAccountHolders(MySQLDatabase database, SecureRandom random)
            throws SQLException {
        List<Integer> ids = new ArrayList<>();
        AccountHoldersDAO dao = database.getAccountHolders();
        Generator<AccountHolder> generator = new AccountHolderGenerator(random);
        try (Connection connection = MySQLDatabase.getConnection()) {
            connection.setAutoCommit(false);
            int accountHoldersCount = ACCOUNT_HOLDERS_COUNT;
            for (int i = 0; i < accountHoldersCount; i++) {
                AccountHolder accountHolder = generator.generate();
                Integer accountHolderId = dao.insert(connection, accountHolder);
                ids.add(accountHolderId);
            }
            connection.commit();
            return ids;
        }
    }

    private static List<Integer> createRandomAccountsForAccountHolders(MySQLDatabase database, SecureRandom random, List<Integer> accountHoldersIds)
            throws SQLException {
        List<Integer> ids = new ArrayList<>();
        AccountsDAO dao = database.getAccounts();
        Generator<Account> generator = new AccountGenerator(random);
        try (Connection connection = MySQLDatabase.getConnection()) {
            connection.setAutoCommit(false);
            for(int accountHolderId: accountHoldersIds) {
                int accountsCount = random.nextInt(MAX_ACCOUNTS_PER_HOLDER_COUNT - MIN_ACCOUNTS_PER_HOLDER_COUNT) +
                        MIN_ACCOUNTS_PER_HOLDER_COUNT;
                for (int i = 0; i < accountsCount; i++) {
                    Account account = generator.generate();
                    account.setAccountHolderId(accountHolderId);
                    Integer accountId = dao.insert(connection, account);
                    ids.add(accountId);
                    connection.commit();
                }
            }
            return ids;
        }
    }

    private static List<Integer> createRandomTransactionsForAccounts(MySQLDatabase database, SecureRandom random, List<Integer> accountsIds)
            throws SQLException {
        List<Integer> ids = new ArrayList<>();

        TransactionsDAO dao = database.getTransactions();
        Generator<Transaction> generator = new TransactionGenerator(random);
        try (Connection connection = MySQLDatabase.getConnection()) {
            connection.setAutoCommit(false);
            for(int accountFromId: accountsIds) {
                int transactionsCount = random.nextInt(MAX_TRANSACTIONS_PER_ACCOUNT_COUNT - MIN_TRANSACTIONS_PER_ACCOUNT_COUNT) +
                        MIN_TRANSACTIONS_PER_ACCOUNT_COUNT;
                for (int i = 0; i < transactionsCount; i++) {
                    int index = random.nextInt(accountsIds.size());
                    Transaction transaction = generator.generate();
                    transaction.setAccountFromId(accountFromId);
                    transaction.setAccountToId(accountsIds.get(index));
                    Integer transactionId = dao.insert(connection, transaction);
                    ids.add(transactionId);
                    connection.commit();
                }
            }
            return ids;
        }
    }
}
