package pl.polsl.tpdia.queries.handler.impl.transaction;

import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;

import java.util.List;

public class TransactionMasmQueryResponseDescriptor extends MasmQueryResponseDescriptor<Transaction> {

    public TransactionMasmQueryResponseDescriptor(List<Transaction> queryResults) {
        super(queryResults);
    }
}