package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public interface MasmUpdateWorker<TModel> extends Runnable {

    void queueUpdate(MasmUpdateDescriptor<TModel> masmUpdateDescriptor);
    List<MasmUpdateDescriptor<TModel>> getMasmUpdateDescriptors();
    void run();
    void stop();
}
