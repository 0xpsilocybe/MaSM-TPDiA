package pl.polsl.tpdia.updates.handler.impl.transaction;

import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

public class TransactionMasmUpdateDescriptor extends MasmUpdateDescriptor<Transaction> {

    public TransactionMasmUpdateDescriptor(UpdateType updateType) {
        super(updateType);
    }
}
