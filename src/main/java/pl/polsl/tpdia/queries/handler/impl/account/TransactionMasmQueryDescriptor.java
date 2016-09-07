package pl.polsl.tpdia.queries.handler.impl.account;

import pl.polsl.tpdia.models.QueryType;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;

/**
 * Created by Szymon on 07.09.2016.
 */
public class TransactionMasmQueryDescriptor extends MasmQueryDescriptor<Transaction> {

    public TransactionMasmQueryDescriptor(QueryType queryType) {
        super(queryType);
    }
}
