package pl.polsl.tpdia.queries.handler;

import com.mysql.jdbc.exceptions.MySQLInvalidAuthorizationSpecException;
import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.dao.Table;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;
import pl.polsl.tpdia.updates.generator.TransactionUpdatesGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Szymon on 30.08.2016.
 */
public class MasmQueryWorkerImpl<TUpdateDescriptor, TDao extends Table<TUpdateDescriptor>>
        extends WorkerHelper implements MasmQueryWorker {

    private final TransactionUpdatesGenerator transactionUpdatesGenerator;
    private final TransactionsDAO dao;

    public MasmQueryWorkerImpl(TransactionUpdatesGenerator transactionUpdatesGenerator, MySQLDatabase db) {
        this.transactionUpdatesGenerator = transactionUpdatesGenerator;
        this.dao = db.getTransactions();
    }

    @Override
    public long getDelayBetweenOperations() {
        return 10;
    }

    @Override
    protected void doOperation() throws InterruptedException {
        try {
            try(Connection connection = MySQLDatabase.getConnection()) {
                //query processing...
                //fetch from updates buffer
                //merge
                Transaction transaction = new Transaction();
                int id = dao.insert(connection, transaction);
                transactionUpdatesGenerator.addTransactionId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MasmQueryResponseDescriptor queryMasm(MasmQueryDescriptor masmQueryDescriptor) {

        // TODO - Create a mechanism of handling queries
        return null;
    }
}
