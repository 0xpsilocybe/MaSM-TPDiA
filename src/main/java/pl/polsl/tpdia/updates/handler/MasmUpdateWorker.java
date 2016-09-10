package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.util.List;

public interface MasmUpdateWorker<TModel extends Model> extends Runnable {

    void queueUpdate(MasmUpdateDescriptor<TModel> masmUpdateDescriptor);
    List<MasmUpdateDescriptor<TModel>> getMasmUpdateDescriptors();
    void run();
    void stop();
}
