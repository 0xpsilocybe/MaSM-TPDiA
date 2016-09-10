package pl.polsl.tpdia.queries.generator;

import pl.polsl.tpdia.helpers.EnumGenerator;
import pl.polsl.tpdia.models.QueryType;
import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;
import pl.polsl.tpdia.queries.handler.MasmQueryWorker;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.queries.handler.impl.transaction.TransactionMasmQueryDescriptor;

import java.security.SecureRandom;

/**
 * Worker generating random SQL analityc queries
 * Created by Szymon on 29.08.2016.
 */
public class QueriesGenerator extends WorkerHelper {

    private final MasmQueryWorker masmQueryWorker;
    private final SecureRandom secureRandom;
    private final EnumGenerator<QueryType> queryTypeGenerator;

    public QueriesGenerator(MasmQueryWorker masmQueryWorker) {
        this.masmQueryWorker = masmQueryWorker;
        this.secureRandom = new SecureRandom();
        this.queryTypeGenerator = new EnumGenerator<>(QueryType.GET_ALL, this.secureRandom);
    }

    @Override
    public long getDelayBetweenOperations() {
        return 100;
    }

    @Override
    public void doOperation() throws InterruptedException {

        QueryType queryType = queryTypeGenerator.generate();
        TransactionMasmQueryDescriptor queryDescriptor = new TransactionMasmQueryDescriptor(queryType);

        switch (queryType) {
            case GET_ALL: {
                break;
            }
            default: {
                throw new EnumConstantNotPresentException(QueryType.class, queryType.toString());
            }
        }

        masmQueryWorker.queueQuery(queryDescriptor);
    }
}
