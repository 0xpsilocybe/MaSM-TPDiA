package pl.polsl.tpdia.queries.handler.impl;

import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.queries.handler.MasmQueryWorkerImpl;
import pl.polsl.tpdia.updates.generator.UpdatesGenerator;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;

import java.sql.SQLException;

/**
 * Created by Szymon on 10.09.2016.
 */
public class TransactionMasmQueryWorkerImpl extends MasmQueryWorkerImpl<Transaction, TransactionsDAO> {

    public TransactionMasmQueryWorkerImpl(
            UpdatesGenerator updatesGenerator,
            MasmUpdateWorker masmUpdateWorker,
            MySQLDatabase database) throws SQLException {

        super(updatesGenerator, masmUpdateWorker, database);

        this.modelDAO = database.getTransactions();
    }
}
