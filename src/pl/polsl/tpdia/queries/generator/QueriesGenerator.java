package pl.polsl.tpdia.queries.generator;

import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;
import pl.polsl.tpdia.queries.handler.MasmQueryWorker;
import pl.polsl.tpdia.helpers.WorkerHelper;

/**
 * Created by Szymon on 29.08.2016.
 */
public class QueriesGenerator extends WorkerHelper {

    private MasmQueryWorker masmQueryWorker;

    public QueriesGenerator(MasmQueryWorker masmQueryWorker) {
        this.masmQueryWorker = masmQueryWorker;
    }

    @Override
    public long getDelayBetweenOperations() {
        return 100;
    }

    @Override
    public void doOperation() throws InterruptedException {

        // TODO - Create a mechanism of generating queries
        MasmQueryResponseDescriptor response = masmQueryWorker.queryMasm(new MasmQueryDescriptor());
    }
}
