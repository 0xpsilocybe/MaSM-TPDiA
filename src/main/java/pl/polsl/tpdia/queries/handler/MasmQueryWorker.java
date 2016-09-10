package pl.polsl.tpdia.queries.handler;

import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;

public interface MasmQueryWorker<TModel extends Model> extends Runnable {

    void queueQuery(MasmQueryDescriptor<TModel> masmUpdateDescriptor);
    void run();
    void stop();
}
