package pl.polsl.tpdia.updates.handler.impl.account;

import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.updates.handler.MasmUpdateCore;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorkerBase;

public class TransactionMasmUpdateWorker extends MasmUpdateWorkerBase<Account> {

    public TransactionMasmUpdateWorker(MasmUpdateCore<Account> masmUpdateCore) {
        super(masmUpdateCore);
    }
}
