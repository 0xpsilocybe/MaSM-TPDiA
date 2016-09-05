package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Szymon on 29.08.2016.
 */
public class MasmUpdateWorkerImpl extends WorkerHelper implements MasmUpdateWorker {

    private final MasmUpdateCore masmUpdateCore;
    private final BlockingQueue<MasmUpdateDescriptor> queuedMasmUpdates;

    public MasmUpdateWorkerImpl(MasmUpdateCore masmUpdateCore) {
        this.masmUpdateCore = masmUpdateCore;
        this.queuedMasmUpdates = new ArrayBlockingQueue<MasmUpdateDescriptor>(10);
    }

    @Override
    public void queueUpdate(MasmUpdateDescriptor masmUpdateDescriptor) {

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

        MasmUpdateDescriptor updateDescriptor = queuedMasmUpdates.take();
        masmUpdateCore.performUpdate(updateDescriptor);
    }
}