package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.List;

public interface MasmUpdateWorker<TModel> extends Runnable {

    void queueUpdate(MasmUpdateDescriptor<TModel> masmUpdateDescriptor);
    List<MasmUpdateDescriptor<TModel>> getMasmUpdateDescriptors();
    void run();
    void stop();
}
