package pl.polsl.tpdia.updates.generator;

import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;

/**
 * Created by Szymon on 29.08.2016.
 */
public class UpdatesGenerator extends WorkerHelper {

    private MasmUpdateWorker masmUpdateWorker;

    public UpdatesGenerator(MasmUpdateWorker masmUpdateWorker) {
        this.masmUpdateWorker = masmUpdateWorker;
    }

    @Override
    public long getDelayBetweenOperations() {
        return 100;
    }

    @Override
    protected void doOperation() throws InterruptedException {

        // TODO - Create a mechanism of generating updates
        //masmUpdateWorker.queueUpdate();
    }
}
