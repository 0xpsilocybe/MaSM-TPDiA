package pl.polsl.tpdia.queries.handler.impl.account;

import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.MasmQueryResponseDescriptor;

import java.util.List;

/**
 * Created by Szymon on 07.09.2016.
 */
public class TransactionMasmQueryResponseDescriptor extends MasmQueryResponseDescriptor<Transaction> {

    public TransactionMasmQueryResponseDescriptor(List<Transaction> queryResults) {
        super(queryResults);
    }
}