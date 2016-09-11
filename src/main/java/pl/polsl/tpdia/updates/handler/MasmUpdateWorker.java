package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.sql.Timestamp;
import java.util.List;

public interface MasmUpdateWorker<TModel> extends Runnable {
    void queueUpdate(MasmUpdateDescriptor<TModel> masmUpdateDescriptor);
    List<MasmUpdateDescriptor<TModel>> getMasmUpdateDescriptors(Timestamp limitTime);
    void run();
    void stop();
}
