package pl.polsl.tpdia.queries.handler;

import jdk.nashorn.internal.ir.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.QueryType;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.generator.TransactionUpdatesGenerator;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MasmQueryWorkerImpl extends WorkerHelper implements MasmQueryWorker<Transaction> {

    private static final Logger logger = LogManager.getLogger(MasmQueryWorkerImpl.class.getName());

    private final TransactionUpdatesGenerator transactionUpdatesGenerator;
    private final MasmUpdateWorker<Transaction> transactionMasmUpdateWorker;
    private final TransactionsDAO transactionsDAO;
    private final Connection connection;
    private final List<MasmUpdateDescriptor<Transaction>> updateDescriptors;
    private final BlockingQueue<MasmQueryDescriptor<Transaction>> queuedMasmQueries;

    public MasmQueryWorkerImpl(
            TransactionUpdatesGenerator transactionUpdatesGenerator,
            MasmUpdateWorker<Transaction> transactionMasmUpdateWorker, MySQLDatabase database) throws SQLException {

        this.queuedMasmQueries = new ArrayBlockingQueue<>(10);
        this.transactionUpdatesGenerator = transactionUpdatesGenerator;
        this.transactionMasmUpdateWorker = transactionMasmUpdateWorker;
        this.connection = database.getConnection();
        this.transactionsDAO = database.getTransactions();
        this.updateDescriptors = transactionMasmUpdateWorker.getMasmUpdateDescriptors();
    }

    @Override
    public long getDelayBetweenOperations() {
        return 10;
    }

    @Override
    public void queueQuery(MasmQueryDescriptor<Transaction> masmUpdateDescriptor) {
        try {
            queuedMasmQueries.put(masmUpdateDescriptor);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.error("Terminated MasmUpdateWorker's task.", ex);
        }
    }

    @Override
    protected void doOperation() throws InterruptedException {

        MasmQueryDescriptor<Transaction> queryDescriptor = queuedMasmQueries.take();

        for(MasmUpdateDescriptor<Transaction> updateDescriptor : new ArrayList<>(updateDescriptors)) {

            try {
                switch (updateDescriptor.getUpdateType()) {
                    case INSERT:
                        int id = transactionsDAO.insert(connection, updateDescriptor.getModel());
                        transactionUpdatesGenerator.addTransactionId(id);
                        break;
                    case UPDATE:
                        transactionsDAO.update(connection, updateDescriptor.getModel());
                        break;
                    case DELETE:
                        transactionsDAO.delete(connection, updateDescriptor.getModel().getId());
                        break;
                    default: {
                        throw new EnumConstantNotPresentException(UpdateType.class, updateDescriptor.getUpdateType().toString());
                    }
                }

                updateDescriptors.remove(updateDescriptor);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        List<Transaction> listOfResults = null;

        try {
            switch (queryDescriptor.getQueryType()) {
                case GET_ALL: {
                    listOfResults = transactionsDAO.selectAll(connection);
                    break;
                }
                default: {
                    throw new EnumConstantNotPresentException(QueryType.class, queryDescriptor.getQueryType().toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.debug(listOfResults != null ? listOfResults.size() : "NULL LIST OF RESULTS");
    }
}
