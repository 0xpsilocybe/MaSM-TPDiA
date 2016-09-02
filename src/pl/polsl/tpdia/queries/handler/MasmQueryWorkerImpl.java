package pl.polsl.tpdia.queries.handler;

import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;

/**
 * Created by Szymon on 30.08.2016.
 */
public class MasmQueryWorkerImpl extends WorkerHelper implements MasmQueryWorker {

    private final MasmQueryCore masmQueryCore;

    public MasmQueryWorkerImpl(MasmQueryCore masmQueryCore) {
        this.masmQueryCore = masmQueryCore;
    }

    @Override
    public long getDelayBetweenOperations() {
        return 10;
    }

    @Override
    protected void doOperation() throws InterruptedException {


    }

    @Override
    public MasmQueryResponseDescriptor queryMasm(MasmQueryDescriptor masmQueryDescriptor) {

        // TODO - Create a mechanism of handling queries
        return null;
    }
}
