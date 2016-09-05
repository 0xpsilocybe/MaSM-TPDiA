package pl.polsl.tpdia.queries.handler;

import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;

/**
 * Query generator contract
 * Created by Szymon on 29.08.2016.
 */
public interface MasmQueryWorker extends Runnable {

    MasmQueryResponseDescriptor queryMasm(MasmQueryDescriptor masmQueryDescriptor);
    void run();
    void stop();
}
