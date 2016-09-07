package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Szymon on 29.08.2016.
 */
public class MasmUpdateWorkerImpl<TUpdateDescriptor>
        extends WorkerHelper implements MasmUpdateWorker<TUpdateDescriptor> {

    private final BlockingQueue<MasmUpdateDescriptor<TUpdateDescriptor>> queuedMasmUpdates;
    private final List<MasmUpdateDescriptor<TUpdateDescriptor>> masmUpdateDescriptors;

    public MasmUpdateWorkerImpl() {
        this.queuedMasmUpdates = new ArrayBlockingQueue<>(10);
        this.masmUpdateDescriptors = new ArrayList<>();
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
        masmUpdateDescriptors.add(updateDescriptor);
    }
}

