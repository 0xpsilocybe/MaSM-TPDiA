package pl.polsl.tpdia.updates.handler.impl.account;

import pl.polsl.tpdia.helpers.UpdateType;
import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

/**
 * Created by Szymon on 06.09.2016.
 */
public class TransactionMasmUpdateDescriptor extends MasmUpdateDescriptor<Transaction> {

    public TransactionMasmUpdateDescriptor(UpdateType updateType) {
        super(updateType);
    }

    @Override
    public void processRecord() {

    }
}
