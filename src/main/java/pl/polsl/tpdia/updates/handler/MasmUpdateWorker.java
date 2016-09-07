package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.dao.Table;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

/**
 * Created by Szymon on 29.08.2016.
 */
public interface MasmUpdateWorker<TUpdateDescriptor, TDao extends Table<TUpdateDescriptor>> {

    void queueUpdate(MasmUpdateDescriptor<TUpdateDescriptor, TDao> masmUpdateDescriptor);
    void run();
    void stop();
}
