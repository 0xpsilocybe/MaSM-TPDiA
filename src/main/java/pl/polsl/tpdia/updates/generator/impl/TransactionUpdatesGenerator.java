package pl.polsl.tpdia.updates.generator.impl;

import pl.polsl.tpdia.helpers.TransactionGenerator;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.generator.UpdatesGenerator;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;

import java.util.List;

public class TransactionUpdatesGenerator extends UpdatesGenerator<Transaction> {

    public TransactionUpdatesGenerator(MasmUpdateWorker<Transaction> masmUpdateWorker, List<Integer> modelIds) {

        super(masmUpdateWorker, modelIds);
        this.modelGenerator = new TransactionGenerator(this.secureRandom);
    }
}
