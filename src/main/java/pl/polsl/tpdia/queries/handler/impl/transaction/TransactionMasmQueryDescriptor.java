package pl.polsl.tpdia.queries.handler.impl.transaction;

import pl.polsl.tpdia.models.QueryType;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.MasmQueryDescriptor;

public class TransactionMasmQueryDescriptor extends MasmQueryDescriptor<Transaction> {

    public TransactionMasmQueryDescriptor(QueryType queryType) {
        super(queryType);
    }
}
