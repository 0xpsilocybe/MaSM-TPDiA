package pl.polsl.tpdia.updates.handler;

import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

/**
 * Created by Szymon on 29.08.2016.
 */
public abstract class MasmUpdateCoreBase<TUpdateDescriptor> implements MasmUpdateCore<TUpdateDescriptor> {

    @Override
    public void performUpdate(MasmUpdateDescriptor<TUpdateDescriptor> updateDescriptor) {

        // TODO - Create a mechanism of consuming updates ... common things
        performSpecificThings(updateDescriptor);

    }

    public abstract void performSpecificThings(MasmUpdateDescriptor<TUpdateDescriptor> updateDescriptor);
}

