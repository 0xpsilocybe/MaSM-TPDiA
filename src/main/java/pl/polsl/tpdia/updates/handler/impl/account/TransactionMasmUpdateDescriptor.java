package pl.polsl.tpdia.updates.handler.impl.account;

import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

/**
 * Created by Szymon on 06.09.2016.
 */
public class TransactionMasmUpdateDescriptor implements MasmUpdateDescriptor<Transaction> {

    @Override
    public Transaction getUpdateDescriptor() {
        return null;
    }
}
