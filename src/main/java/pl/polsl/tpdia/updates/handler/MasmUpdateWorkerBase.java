package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Szymon on 29.08.2016.
 */
public abstract class MasmUpdateWorkerBase<TUpdateDescriptor>
        extends WorkerHelper implements MasmUpdateWorker<TUpdateDescriptor> {

    private final MasmUpdateCore<TUpdateDescriptor> masmUpdateCore;
    private final BlockingQueue<MasmUpdateDescriptor<TUpdateDescriptor>> queuedMasmUpdates;

    public MasmUpdateWorkerBase(MasmUpdateCore<TUpdateDescriptor> masmUpdateCore) {
        this.masmUpdateCore = masmUpdateCore;
        this.queuedMasmUpdates = new ArrayBlockingQueue<>(10);
    }

    @Override
    public void queueUpdate(MasmUpdateDescriptor<TUpdateDescriptor> masmUpdateDescriptor) {

        try {
            queuedMasmUpdates.put(masmUpdateDescriptor);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.error("Terminated MasmUpdateWorker's task.", ex);
        }
    }

    @Override
    public long getDelayBetweenOperations() {
        return 100;
    }

    @Override
    public void doOperation() throws InterruptedException {

        MasmUpdateDescriptor<TUpdateDescriptor> updateDescriptor = queuedMasmUpdates.take();
        masmUpdateCore.performUpdate(updateDescriptor);
    }
}

