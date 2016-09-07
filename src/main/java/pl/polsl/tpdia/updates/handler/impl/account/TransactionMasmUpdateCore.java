package pl.polsl.tpdia.updates.handler.impl.account;

import pl.polsl.tpdia.dao.AccountsDAO;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.handler.MasmUpdateCoreBase;

class TransactionMasmUpdateCore extends MasmUpdateCoreBase<Transaction> {

    TransactionsDAO accountsDAO;

    @Override
    public void performSpecificThings(MasmUpdateDescriptor<Transaction> updateDescriptor) {

    }
}
