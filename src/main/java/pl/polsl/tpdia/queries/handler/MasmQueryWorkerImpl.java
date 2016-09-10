package pl.polsl.tpdia.queries.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.dao.Table;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.models.QueryType;
import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.generator.UpdatesGenerator;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class MasmQueryWorkerImpl<TModel extends Model, TDao extends Table<TModel>> extends WorkerHelper implements MasmQueryWorker<TModel> {

    private static final Logger logger = LogManager.getLogger(MasmQueryWorkerImpl.class.getName());

    private final UpdatesGenerator<TModel> updatesGenerator;
    private final MasmUpdateWorker<TModel> masmUpdateWorker;
    protected TDao modelDAO;
    private final Connection connection;

    private final BlockingQueue<MasmQueryDescriptor<TModel>> queuedMasmQueries;

    public MasmQueryWorkerImpl(
            UpdatesGenerator<TModel> updatesGenerator,
            MasmUpdateWorker<TModel> masmUpdateWorker, MySQLDatabase database) throws SQLException {

        this.queuedMasmQueries = new ArrayBlockingQueue<>(10);
        this.updatesGenerator = updatesGenerator;
        this.masmUpdateWorker = masmUpdateWorker;
        this.connection = database.getConnection();
    }

    @Override
    public long getDelayBetweenOperations() {
        return 10;
    }

    @Override
    public void queueQuery(MasmQueryDescriptor<TModel> masmUpdateDescriptor) {
        try {
            queuedMasmQueries.put(masmUpdateDescriptor);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.error("Terminated MasmUpdateWorker's task.", ex);
        }
    }

    @Override
    protected void doOperation() throws InterruptedException {

        MasmQueryDescriptor<TModel> queryDescriptor = queuedMasmQueries.take();

        List<MasmUpdateDescriptor<TModel>> updateDescriptors = masmUpdateWorker.getMasmUpdateDescriptors();

        for (MasmUpdateDescriptor<TModel> updateDescriptor : updateDescriptors) {
            try {
                switch (updateDescriptor.getUpdateType()) {
                    case INSERT:
                        int id = modelDAO.insert(connection, updateDescriptor.getModel());
                        updatesGenerator.addModelId(id);
                        break;
                    case UPDATE:
                        modelDAO.update(connection, updateDescriptor.getModel());
                        break;
                    case DELETE:
                        modelDAO.delete(connection, updateDescriptor.getModel().getId());
                        break;
                    default: {
                        throw new EnumConstantNotPresentException(UpdateType.class, updateDescriptor.getUpdateType().toString());
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        List<TModel> listOfResults = null;

        try {
            switch (queryDescriptor.getQueryType()) {
                case GET_ALL: {
                    listOfResults = modelDAO.selectAll(connection);
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
