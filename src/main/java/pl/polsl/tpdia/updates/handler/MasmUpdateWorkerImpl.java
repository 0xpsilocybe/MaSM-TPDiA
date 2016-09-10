package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MasmUpdateWorkerImpl<TModel extends Model>
        extends WorkerHelper implements MasmUpdateWorker<TModel> {

    private final BlockingQueue<MasmUpdateDescriptor<TModel>> queuedMasmUpdates;
    private final List<MasmUpdateDescriptor<TModel>> masmUpdateDescriptors;

    public MasmUpdateWorkerImpl() {
        this.queuedMasmUpdates = new ArrayBlockingQueue<>(10);
        this.masmUpdateDescriptors = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void queueUpdate(MasmUpdateDescriptor<TModel> masmUpdateDescriptor) {
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

        MasmUpdateDescriptor<TModel> updateDescriptor = queuedMasmUpdates.take();
        masmUpdateDescriptors.add(updateDescriptor);
    }

    @Override
    public List<MasmUpdateDescriptor<TModel>> getMasmUpdateDescriptors() {
        return masmUpdateDescriptors;
    }
}

