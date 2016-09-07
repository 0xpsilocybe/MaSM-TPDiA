package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.dao.Table;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Szymon on 29.08.2016.
 */
public class MasmUpdateWorkerImpl<TUpdateDescriptor, TDao extends Table<TUpdateDescriptor>>
        extends WorkerHelper implements MasmUpdateWorker<TUpdateDescriptor, TDao> {

    private final BlockingQueue<MasmUpdateDescriptor<TUpdateDescriptor, TDao>> queuedMasmUpdates;
    private final List<MasmUpdateDescriptor<TUpdateDescriptor, TDao>> masmUpdateDescriptors;

    public MasmUpdateWorkerImpl() {
        this.queuedMasmUpdates = new ArrayBlockingQueue<>(10);
        this.masmUpdateDescriptors = new ArrayList<>();
    }

    @Override
    public void queueUpdate(MasmUpdateDescriptor<TUpdateDescriptor, TDao> masmUpdateDescriptor) {
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

        MasmUpdateDescriptor<TUpdateDescriptor, TDao> updateDescriptor = queuedMasmUpdates.take();
        masmUpdateDescriptors.add(updateDescriptor);
    }
}

