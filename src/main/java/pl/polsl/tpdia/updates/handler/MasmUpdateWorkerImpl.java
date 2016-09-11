package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class MasmUpdateWorkerImpl<TModel>
        extends WorkerHelper implements MasmUpdateWorker<TModel> {

    private final BlockingQueue<MasmUpdateDescriptor<TModel>> queuedMasmUpdates;
    private final CopyOnWriteArrayList<MasmUpdateDescriptor<TModel>> masmUpdateDescriptors;

    public MasmUpdateWorkerImpl() {
        super("Update handler");
        this.queuedMasmUpdates = new ArrayBlockingQueue<>(10);
        this.masmUpdateDescriptors = new CopyOnWriteArrayList<>();
    }

    @Override
    public void queueUpdate(MasmUpdateDescriptor<TModel> masmUpdateDescriptor) {
        try {
            logger.trace("Adding update descriptor to queue: " + masmUpdateDescriptor.toString());
            queuedMasmUpdates.put(masmUpdateDescriptor);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.error("Terminated " + this.getWorkerName() + " task.", ex);
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
    public List<MasmUpdateDescriptor<TModel>> getMasmUpdateDescriptors(Timestamp limitTime) {
        ArrayList<MasmUpdateDescriptor<TModel>> updates = new ArrayList<>();
        for(MasmUpdateDescriptor<TModel> descriptor : this.masmUpdateDescriptors) {
            if (limitTime.after(descriptor.getTimestamp())) {
                updates.add(descriptor);
            } else {
                break;
            }
        }
        this.masmUpdateDescriptors.removeAll(updates);
        return updates;
    }
}

