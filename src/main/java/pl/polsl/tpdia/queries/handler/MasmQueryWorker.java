package pl.polsl.tpdia.queries.handler;

import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;

/**
 * Query generator contract
 * Created by Szymon on 29.08.2016.
 */
public interface MasmQueryWorker<TModel> extends Runnable {

    void queueQuery(MasmQueryDescriptor<TModel> masmUpdateDescriptor);
    void run();
    void stop();
}
