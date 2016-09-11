package pl.polsl.tpdia.queries.generator;

import pl.polsl.tpdia.helpers.EnumGenerator;
import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.models.QueryType;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.queries.handler.MasmQueryWorker;
import pl.polsl.tpdia.helpers.WorkerHelper;

import java.security.SecureRandom;

public class QueriesGenerator<TModel extends Model> extends WorkerHelper {

    private final MasmQueryWorker<TModel> masmQueryWorker;
    private final SecureRandom secureRandom;
    private final EnumGenerator<QueryType> queryTypeGenerator;

    public QueriesGenerator(MasmQueryWorker<TModel> masmQueryWorker) {
        super("Queries generator");
        this.masmQueryWorker = masmQueryWorker;
        this.secureRandom = new SecureRandom();
        this.queryTypeGenerator = new EnumGenerator<>(QueryType.GET_ALL, this.secureRandom);
    }

    @Override
    public long getDelayBetweenOperations() {
        return 1000;
    }

    @Override
    public void doOperation() throws InterruptedException {
        QueryType queryType = queryTypeGenerator.generate();
        MasmQueryDescriptor<TModel> queryDescriptor = new MasmQueryDescriptor<>(queryType);

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
